package studio.clouthin.next.miniadmin.framework.attachment.support;

import org.springframework.web.multipart.MultipartFile;
import studio.clouthin.next.miniadmin.framework.attachment.dto.response.AttachmentSummary;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface AttachmentsRestSupport {

    void download(String storedFilename, HttpServletResponse response) throws IOException;

    AttachmentSummary upload(MultipartFile file) throws IOException;

    List<AttachmentSummary> list(String[] names);
}
