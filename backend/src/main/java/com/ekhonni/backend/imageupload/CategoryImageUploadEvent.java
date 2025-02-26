/**
 * Author: Rifat Shariar Sakil
 * Time: 2:44 PM
 * Date: 2/26/25
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.imageupload;

import java.util.List;

public record CategoryImageUploadEvent(Long categoryId, byte[] imageBytes, String filename, String contentType) {}

