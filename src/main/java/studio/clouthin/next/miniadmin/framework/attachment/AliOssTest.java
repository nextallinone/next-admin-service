//package studio.clouthin.next.miniadmin.framework.attachment;
//
//import in.clouthink.daas.fss.core.FileStorage;
//import in.clouthink.daas.fss.core.StoreFileResponse;
//import in.clouthink.daas.fss.core.StoredFileObject;
//import in.clouthink.daas.fss.support.DefaultStoreFileRequest;
//import in.clouthink.daas.fss.util.MetadataUtils;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.core.io.FileSystemResource;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit4.SpringRunner;
//import studio.clouthin.next.miniadmin.MiniAdminApplication;
//
//import javax.annotation.Resource;
//import java.io.FileOutputStream;
//import java.io.IOException;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = MiniAdminApplication.class)
//public class AliOssTest {
//
//    @Resource
//    private FileStorage fileStorage;
//
//    private FileSystemResource pdfResource = new FileSystemResource("/Users/vanish/Downloads/tt.xlsx");
//
//    @Test
//    public void test() throws IOException {
//        Assert.assertTrue(pdfResource.exists());
//
//        DefaultStoreFileRequest request = new DefaultStoreFileRequest();
//        request.setOriginalFilename(pdfResource.getFilename());
//        request.setContentType(MediaType.APPLICATION_PDF.toString());
//        StoreFileResponse response = fileStorage.store(pdfResource.getInputStream(), request);
//
//        Assert.assertEquals("alioss", response.getProviderName());
//
//        StoredFileObject storedFileObject = response.getStoredFileObject();
//        Assert.assertNotNull(storedFileObject);
//
//        storedFileObject = fileStorage.findByStoredFilename(storedFileObject.getStoredFilename());
//        String saveToFilename = MetadataUtils.generateFilename(request);
//        storedFileObject.writeTo(new FileOutputStream(saveToFilename), 1024 * 4);
//
//        storedFileObject = fileStorage.delete(storedFileObject.getStoredFilename());
//        Assert.assertNull(storedFileObject.getImplementation());
//    }
//}
