package messenger.messenger.business.filestore;

public interface FileStore {

    /**
     * id를 입력 받아서 채팅 파일 생성
     * @param id
     * @return 폴더 경로
     */
    String writeFolder(String id);

    /**
     * folderPath를 입력받아서 message를 파일에 쓰기
     * @param folderPath
     * @return
     */
    String writeFile(String folderPath, String message);


    /**
     * 기존의 입력 경로를 받아 새로운 경로에 파일을 복사
     * @param originPath
     * @param newPath
     */
    void copyFile(String originPath, String newPath);

}
