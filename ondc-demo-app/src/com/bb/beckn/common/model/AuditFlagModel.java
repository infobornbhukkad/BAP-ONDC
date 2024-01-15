// 
// Decompiled by Procyon v0.5.36
// 

package com.bb.beckn.common.model;

public class AuditFlagModel
{
    private boolean http;
    private boolean database;
    private boolean file;
    
    public boolean isHttp() {
        return this.http;
    }
    
    public boolean isDatabase() {
        return this.database;
    }
    
    public boolean isFile() {
        return this.file;
    }
    
    public void setHttp(final boolean http) {
        this.http = http;
    }
    
    public void setDatabase(final boolean database) {
        this.database = database;
    }
    
    public void setFile(final boolean file) {
        this.file = file;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof AuditFlagModel)) {
            return false;
        }
        final AuditFlagModel other = (AuditFlagModel)o;
        return other.canEqual(this) && this.isHttp() == other.isHttp() && this.isDatabase() == other.isDatabase() && this.isFile() == other.isFile();
    }
    
    protected boolean canEqual(final Object other) {
        return other instanceof AuditFlagModel;
    }
    
    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * 59 + (this.isHttp() ? 79 : 97);
        result = result * 59 + (this.isDatabase() ? 79 : 97);
        result = result * 59 + (this.isFile() ? 79 : 97);
        return result;
    }
    
    @Override
    public String toString() {
        return "AuditFlagModel(http=" + this.isHttp() + ", database=" + this.isDatabase() + ", file=" + this.isFile() + ")";
    }
}
