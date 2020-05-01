package com.example.equityfeedsprocessing.quartz;

import com.example.equityfeedsprocessing.util.ProcessingLogic;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class SampleJobService {

    @Autowired
    private ProcessingLogic processingLogic;

    @Autowired
    private ParseCSVFile parseCSVFile;

    @Value("${nasdaq.outboundTopicName}")
    private String nasdaqOutboundTopicName;

    @Value("${ftp.server}")
    private String server;

    @Value("${ftp.port}")
    private int port;

    @Value("${ftp.user}")
    private String user;

    @Value("${ftp.pass}")
    private String pass;

    private static final Logger logger = LoggerFactory.getLogger(SampleJobService.class);

    public static final String CSV_FILE = "src/main/resources/nasdaqdata_invalid_1.csv";

    public void executeSampleJob() throws IOException {

        logger.info(" START | In executeSampleJob method of SampleJobService class.");

        FTPClient ftpClient = new FTPClient();
        try {

            logger.info("Trying to connect to the remote FTP Server to download the file.");

            ftpClient.connect(server, port);
            ftpClient.login(user, pass);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            String remoteFile1 = "/equityFeedsProcessing/nasdaqdata_invalid_1.csv";
            File downloadFile1 = new File(CSV_FILE);
            try(OutputStream outputStream1 = new BufferedOutputStream(new FileOutputStream(downloadFile1))) {
                boolean success = ftpClient.retrieveFile(remoteFile1, outputStream1);
                outputStream1.close();

                if (success) {
                    logger.info("File downloaded successfully from remote FTP server.");

                    logger.info("START | Calling the parseCSVFile method of the parseCSVFile class.");

                    parseCSVFile.parse();

                    logger.info("END | Calling the parseCSVFile method of the parseCSVFile class.");

                } else {
                    logger.error("File could not be downloaded from remote FTP Server.");
                }

                logger.info(" END | In executeSampleJob method of SampleJobService class.");
            }
        } catch (IOException ex) {
            logger.error("IO Exception: {}", ex);
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
                logger.error("IO Exception: {}", ex);
            }
        }

    }

}
