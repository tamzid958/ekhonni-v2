/**
 * Author: Rifat Shariar Sakil
 * Time: 2:08 PM
 * Date: 2/25/25
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.imageupload;

import java.util.List;

public record ProductImageUploadEvent(Long productId, List<byte[]> imageBytes, List<String> filenames, List<String> contentTypes) {}
