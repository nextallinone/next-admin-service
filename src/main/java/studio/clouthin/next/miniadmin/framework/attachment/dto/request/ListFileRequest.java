package studio.clouthin.next.miniadmin.framework.attachment.dto.request;

import lombok.Data;

@Data
public class ListFileRequest {
    private String[] storedFilenames;
}
