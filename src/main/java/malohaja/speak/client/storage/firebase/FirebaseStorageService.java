package malohaja.speak.client.storage.firebase;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.firebase.cloud.StorageClient;
import lombok.RequiredArgsConstructor;
import malohaja.speak.client.storage.StorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class FirebaseStorageService implements StorageService {

    @Value("${app.firebase-bucket}")
    private String firebaseBucket;

    @Value("${app.firebase-configuration-file}")
    private String firebaseConfigPath;

    private Bucket getBucket(){
        return StorageClient.getInstance().bucket(firebaseBucket);
    }

    @Override
    public String upload(MultipartFile file) throws IOException {

        String name = generateFileName(file.getOriginalFilename());
        Bucket bucket = getBucket();
        Blob blob = bucket.create(name, file.getBytes(), file.getContentType());
        return blob.getMediaLink();
    }
}
