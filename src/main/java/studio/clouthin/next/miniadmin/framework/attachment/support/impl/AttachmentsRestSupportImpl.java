package studio.clouthin.next.miniadmin.framework.attachment.support.impl;

import com.google.common.collect.Lists;
import in.clouthink.daas.fss.core.FileStorage;
import in.clouthink.daas.fss.core.StoreFileResponse;
import in.clouthink.daas.fss.core.StoredFileObject;
import in.clouthink.daas.fss.support.DefaultStoreFileRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import studio.clouthin.next.miniadmin.framework.attachment.dto.response.AttachmentSummary;
import studio.clouthin.next.miniadmin.framework.attachment.support.AttachmentsRestSupport;
import studio.clouthin.next.shared.utils.SecurityUtils;
import studio.clouthin.next.shared.utils.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class AttachmentsRestSupportImpl implements AttachmentsRestSupport {

    @Autowired
    private FileStorage fileStorage;


    @Override
    public void download(String StoredFilename, HttpServletResponse response) throws IOException {
        // TODO Should I Save The Download Histories ?
        StoredFileObject storedFileObject = fileStorage.findByStoredFilename(StoredFilename);
        OutputStream out = response.getOutputStream();
        try {
//            OSSObject ossObject = ((OSSObject)storedFileObject.getImplementation());
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-Type", "application/octet-stream;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + storedFileObject.getOriginalFilename());
            storedFileObject.writeTo(out, 1024 * 4);
            out.flush();
            out.close();
        } catch (Throwable t) {
            log.error(t.getMessage());
        } finally {
            out.flush();
            out.close();
        }
    }

    @Override
    public AttachmentSummary upload(MultipartFile file) throws IOException {
        DefaultStoreFileRequest request = new DefaultStoreFileRequest();
        request.setOriginalFilename(URLEncoder.encode(file.getOriginalFilename(), "UTF-8"));
        request.setContentType(file.getContentType());
        request.setUploadedBy(URLEncoder.encode(SecurityUtils.getUserDetails().getUsername(), "UTF-8"));
        StoreFileResponse response = fileStorage.store(file.getInputStream(), request);
        return AttachmentSummary.from(response.getStoredFileObject());
    }

    @Override
    public List<AttachmentSummary> list(String[] names) {
        return Lists.newArrayList(names)
                .stream()
                .filter(StringUtils::isNotBlank)
                .map(name -> AttachmentSummary.from(fileStorage.findByStoredFilename(name)))
                .collect(Collectors.toList());
    }
}
