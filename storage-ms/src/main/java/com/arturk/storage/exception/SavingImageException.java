package com.arturk.storage.exception;

import com.arturk.common.exception.TechnicalMarketAppException;

public class SavingImageException extends TechnicalMarketAppException {

  private final static String code = "STORAGE_MS-04";

  public SavingImageException() {
    this(null);
  }

  public SavingImageException(String details) {
    super(code, "Exception during saving product image", details);
  }
}