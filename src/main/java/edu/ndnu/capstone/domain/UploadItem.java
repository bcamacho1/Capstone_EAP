package edu.ndnu.capstone.domain;

//import javax.persistence.Transient;

import org.springframework.web.multipart.commons.CommonsMultipartFile;


public class UploadItem
{
  //private String name;
  private CommonsMultipartFile fileData;
  //@Transient
  //private byte[] content;

  //public String getName()
  //{
  //  return name;
  //}

  //public void setName(String name)
  //{
  //  this.name = name;
  //}

  public CommonsMultipartFile getFileData()
  {
    return fileData;
  }

  public void setFileData(CommonsMultipartFile fileData)
  {
    this.fileData = fileData;
  }
}
