package studio.clouthin.next.miniadmin.framework.attachment.dto.response;

import in.clouthink.daas.fss.core.StoredFileObject;
import in.clouthink.daas.fss.repackage.org.apache.commons.io.FilenameUtils;
import lombok.Data;
import studio.clouthin.next.shared.utils.StringUtils;

@Data
public class AttachmentSummary {
    private String uid;

    private String name;

    private String status = "done";

    private AttachmentResponse response;

    private String url;

    public static AttachmentSummary from(StoredFileObject storedFileObject) {
        AttachmentSummary summary = new AttachmentSummary();
        summary.setUid(storedFileObject.getStoredFilename());
        if (StringUtils.isNotEmpty(storedFileObject.getOriginalFilename())) {
            summary.setName(storedFileObject.getOriginalFilename());
        } else {
            summary.setName(FilenameUtils.getName(storedFileObject.getStoredFilename()));
        }
        summary.setResponse(new AttachmentResponse());
        summary.setUrl("/attachments/download");
//        summary.setUrl(storedFileObject.getFileUrl());
        return summary;
    }


}
