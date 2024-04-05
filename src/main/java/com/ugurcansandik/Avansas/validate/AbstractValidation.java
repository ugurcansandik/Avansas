package com.ugurcansandik.Avansas.validate;

public abstract class AbstractValidation{
    public abstract <T> String validate(T object);
    public abstract <T> boolean nullCheck(T object);
}
