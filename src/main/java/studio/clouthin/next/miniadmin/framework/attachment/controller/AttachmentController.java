package studio.clouthin.next.miniadmin.framework.attachment.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import studio.clouthin.next.miniadmin.framework.attachment.dto.request.DownloadFileRequest;
import studio.clouthin.next.miniadmin.framework.attachment.dto.request.ListFileRequest;
import studio.clouthin.next.miniadmin.framework.attachment.dto.response.AttachmentSummary;
import studio.clouthin.next.miniadmin.framework.attachment.support.AttachmentsRestSupport;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/attachments")
public class AttachmentController {

    @Autowired
    private AttachmentsRestSupport attachmentsRestSupport;

    @ApiOperation(value = "上传附件")
    @PostMapping(value = {"/upload"})
    public AttachmentSummary upload(@RequestBody MultipartFile file) throws IOException {
        return attachmentsRestSupport.upload(file);
    }

    @ApiOperation(value = "下载附件")
    @PostMapping(value = {"/download"})
    public void download(@RequestBody DownloadFileRequest downloadFileRequest, HttpServletResponse response) throws IOException {
//        JwtUser user = (JwtUser) SecurityUtils.getUserDetails();
        String storedFilename = downloadFileRequest.getStoredFilename();
        attachmentsRestSupport.download(storedFilename, response);
    }


    @ApiOperation(value = "附件列表")
    @PostMapping(value = {"/list"})
    public List<AttachmentSummary> list(@RequestBody ListFileRequest listFileRequest) {
        return attachmentsRestSupport.list(listFileRequest.getStoredFilenames());
    }

}
