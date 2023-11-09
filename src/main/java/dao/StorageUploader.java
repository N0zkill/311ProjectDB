package dao;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;

public class StorageUploader {
    private BlobContainerClient containerClient;

    public StorageUploader( ) {
        this.containerClient = new BlobContainerClientBuilder()
                .connectionString("DefaultEndpointsProtocol=https;AccountName=karaoglucsc311storage;AccountKey=MEkjCK8tch26Uwh8vnBGKSZzl3URL9HtnWYSsL1pgyQxqi8pHEo6ay+VNFw5C1lCKO9WI7pgdE2G+AStAorpvw==;EndpointSuffix=core.windows.net")
                .containerName("media-files")
                .buildClient();
    }

    public void uploadFile(String filePath, String blobName) {
        BlobClient blobClient = containerClient.getBlobClient(blobName);
        blobClient.uploadFromFile(filePath,true);
    }
    public BlobContainerClient getContainerClient(){
        return containerClient;
    }
}
