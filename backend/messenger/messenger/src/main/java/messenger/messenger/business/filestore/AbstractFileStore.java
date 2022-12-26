package messenger.messenger.business.filestore;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.io.*;

@Slf4j
public abstract class AbstractFileStore implements FileStore {

    @Value("${file.dir}")
    private String fileDir;

    /**
     * id를 입력 받아서 채팅 파일 생성
     *
     * @param id
     * @return 폴더 경로
     */
    @Override
    public String writeFolder(String id) {

        File folder = new File(fileDir + "/" + id);

        if (!folder.exists()) {
            try{
                folder.mkdirs();
                log.info("folder path = {}", folder.getPath());
            } catch (Exception e) {
                e.getStackTrace();
                log.error(e.getMessage());
            }
        }

        return folder.getPath();
    }

    /**
     * folderPath를 입력받아서 message를 파일에 쓰기
     *
     * @param folderPath
     * @param message
     * @return
     */
    @Override
    public String writeFile(String folderPath, String message) {
        return null;
    }

    /**
     * 기존의 입력 경로를 받아 새로운 경로에 파일을 복사
     *
     * @param originPath
     * @param newPath
     */
    @Override
    public void copyFile(String originPath, String newPath) {

        File file = new File(originPath);
        File newFile = new File(newPath);

        try{

            FileInputStream inputStream = new FileInputStream(file);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

            FileOutputStream outputStream = new FileOutputStream(newFile);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);

            byte[] buff = new byte[1024];

            int readData;
            while((readData = bufferedInputStream.read(buff)) > 0) {
                bufferedOutputStream.write(buff, 0, readData);
            }

            inputStream.close();
            outputStream.close();

            bufferedInputStream.close();
            bufferedOutputStream.close();


        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }
}
