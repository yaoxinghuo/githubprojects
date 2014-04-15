package com.terrynow.springtest.controller;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.terrynow.springtest.util.JsonResult;

/**
 * @author Terry E-mail: yaoxinghuo at 126 dot com
 * @date Apr 15, 2014 1:55:11 PM
 * @description
 */
@Controller
@RequestMapping("/upload")
public class UploadController {

	@RequestMapping(value = "/fileUpload", method = RequestMethod.POST)
	public @ResponseBody JsonResult fileUpload1(HttpServletRequest request)
			throws IllegalStateException, IOException {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());

		// 检查form是否有enctype="multipart/form-data"
		if (multipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;

			Iterator<String> iter = multiRequest.getFileNames();
			while (iter.hasNext()) {

				// 由CommonsMultipartFile继承而来,拥有上面的方法.
				MultipartFile file = multiRequest.getFile(iter.next());
				if (file != null) {
					String basePath = request.getServletContext().getRealPath(
							"/files");
					String fileName = "demoUpload" + file.getOriginalFilename();
					File localFile = new File(basePath, fileName);
					file.transferTo(localFile);
				}

			}
		}
		return new JsonResult(true, "OK");
	}
}
