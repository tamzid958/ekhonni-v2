/**
 * Author: Rifat Shariar Sakil
 * Time: 2:11 PM
 * Date: 2/25/25
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.imageupload;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

@RequiredArgsConstructor
public class InMemoryMultipartFile implements MultipartFile {

    private final byte[] fileContent;
    private final String fileName;
    private final String contentType;

    @Override
    public String getName() {
        return fileName;
    }

    @Override
    public String getOriginalFilename() {
        return fileName;
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public boolean isEmpty() {
        return fileContent.length == 0;
    }

    @Override
    public long getSize() {
        return fileContent.length;
    }

    @Override
    public byte[] getBytes() {
        return fileContent;
    }

    @Override
    public InputStream getInputStream() {
        return new ByteArrayInputStream(fileContent);
    }

    @Override
    public void transferTo(java.io.File dest) {
        throw new UnsupportedOperationException("Not implemented");
    }
}

