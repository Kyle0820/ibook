package com.trs.ibook.service.service;

import com.alibaba.fastjson.JSONObject;
import com.season.common.StrKit;
import com.trs.ibook.core.exception.IBookParamException;
import com.trs.ibook.service.dao.BookInfoDAO;
import com.trs.ibook.service.dao.OriginPicDAO;
import com.trs.ibook.service.pojo.OriginPic;
import org.apache.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import static com.ServiceApp.QUEUE;

/**
 * Title:生产者消费者模式
 * Description:PDF转页的生产者
 * Copyright: 2019 北京拓尔思信息技术股份有限公司 版权所有.保留所有权
 * Company:北京拓尔思信息技术股份有限公司(TRS)
 * Project: ibook
 * Author: KylerTien
 * Create Time:19-4-14 09:33
 */
@Component
public class PDFToOriginService {

    @Autowired
    private AmqpTemplate amqpTemplate;
    @Autowired
    private BookInfoDAO bookInfoDAO;
    @Autowired
    private OriginPicDAO originPicDAO;
    @Value("${ibook.service.imageUpload.baseDir}")
    private String baseDir;
    @Value("${ibook.service.imageUpload.frontDir}")
    private String frontDir;
    private static final Logger logger = Logger.getLogger(PDFToOriginService.class);

    /**
     * 切割PDF为图片
     * 注意:这里使用生产者消费者模式,原图PDF作为生产者切割产出大图,存入origin文件夹,以及写库;
     * 大图作为通知,以json形式通知到消费者;
     * 切割工具作为消费者,收到通知并且切割,同时生成略缩图,以及写库;
     */
    public String cutPDF(String pdfUrl, Integer bookId, Integer startBlankNum, Integer endBlankNum) {
        //首先根据bookId, 获取到文件夹名称
        String albumName = bookInfoDAO.getLocationNameById(bookId);
        if (StrKit.isEmpty(albumName)) {
            return "不正确的bookId";
        }
        // 将pdf转图片 并且自定义图片得格式大小
        File file = new File(frontDir + pdfUrl);
        if (!file.exists()) {
            return "不存在的PDF路径";
        }
        try {
            PDDocument doc = PDDocument.load(file);
            PDFRenderer renderer = new PDFRenderer(doc);
            int pageCount = doc.getNumberOfPages();
            //需要校验起始空白页和结束空白页是否正确
            if (startBlankNum >= pageCount * 2) {
                return "起始空白页必须小于总页码数量";
            }
            //去除起始空白页后的
            if (endBlankNum >= pageCount * 2 - startBlankNum) {
                return "起始空白页码数和结束空白页码数之和必须小于PDF总页码，总页码数为" + pageCount * 2 + "页";
            }
            logger.info("开始对上传的PDF切页,当前PDF有" + pageCount + "页");
            for (int i = 0; i < pageCount; i++) {
                logger.info("开始切出第" + i + "页");
                BufferedImage image = renderer.renderImageWithDPI(i, 144);
                String originPath = baseDir + albumName + "/origin/" + albumName + "_" + (i + 1) + ".png";
                File originFile = new File(originPath);
                if (!originFile.getParentFile().exists()) {
                    boolean result = originFile.getParentFile().mkdirs();
                    if (!result) {
                        logger.error("[print by tk]创建目录失败!");
                    }
                }
                //写出切出的单页图
                ImageIO.write(image, "png", originFile);
                //存表
                OriginPic originPic = new OriginPic();
                originPic.setIsDelete(0);
                originPic.setBookId(bookId);
                originPic.setCreateTime(new Date());
                originPic.setCreateUserId(null);
                originPic.setSerialNo(i + 1);
                originPic.setPicUrl(originPath.replace(frontDir, ""));
                originPicDAO.save(originPic);
                //存文件存表后,通知消费者切图,生成缩略图
                //使用jsonObject进行消息通知
                JSONObject data = new JSONObject();
                data.put("originPath", originPath);
                data.put("targetPath", baseDir + albumName + "/normal/" + albumName);
                data.put("bookId", bookId);
                Integer pageIndex1 = i * 2 + 1;
                Integer pageIndex2 = i * 2 + 2;
                //处理起始的空白页
                pageIndex1 = pageIndex1 - startBlankNum <= 0 ? null : pageIndex1 - startBlankNum;
                pageIndex2 = pageIndex2 - startBlankNum <= 0 ? null : pageIndex2 - startBlankNum;
                //处理结束的空白页
                if (2 * pageCount - endBlankNum < i * 2 + 1) {
                    pageIndex1 = null;
                }
                if (2 * pageCount - endBlankNum < i * 2 + 2) {
                    pageIndex2 = null;
                }
                data.put("pageIndex1", pageIndex1);
                data.put("pageIndex2", pageIndex2);
                this.amqpTemplate.convertAndSend(QUEUE, data.toJSONString());
            }
            doc.close();
        } catch (IOException e) {
            logger.error("[print by tk]PDF切图出现异常!异常信息为:", e);
        }
        return "";
    }
}
