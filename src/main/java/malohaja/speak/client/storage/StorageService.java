package malohaja.speak.client.storage;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public interface StorageService {


    /**
     * @param file 업로드 할 파일
     * @return 접근할 수 있는 URI
     * @throws IOException
     */
    String upload(MultipartFile file) throws IOException;
    
    default String getExtension(String originalFileName) {
        return StringUtils.getFilenameExtension(originalFileName);
    }

    default String generateFileName(String originalFileName) {
        return UUID.randomUUID().toString() + "." + getExtension(originalFileName);
    }

}
