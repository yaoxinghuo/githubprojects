package com.terrynow.springtest.controller;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

	private static Log log = LogFactory.getLog(UploadController.class);

	@RequestMapping(value = "/fileUpload", method = RequestMethod.POST)
	public @ResponseBody JsonResult fileUpload(
			HttpServletRequest request,
			@RequestParam(value = "someKey", required = false, defaultValue = "Default Key") String someKey,
			@RequestParam(value = "someOtherKey", required = false) int someOtherKey)
			throws IllegalStateException, IOException {
		log.info("param someKey: " + someKey);
		log.info("param someOtherKey: " + someOtherKey);
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
					File baseDir = new File(request.getServletContext()
							.getRealPath("/files"));
					if (!baseDir.exists())
						baseDir.mkdirs();
					String fileName = "demoUpload-"
							+ file.getOriginalFilename();
					File localFile = new File(baseDir, fileName);
					file.transferTo(localFile);
				}

			}
		}
		return new JsonResult(true, "OK");
	}
}
