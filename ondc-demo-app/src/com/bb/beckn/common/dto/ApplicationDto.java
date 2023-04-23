// 
// Decompiled by Procyon v0.5.36
// 

package com.bb.beckn.common.dto;

public class ApplicationDto
{
    private KeyIdDto keyIdDto;
    
    public KeyIdDto getKeyIdDto() {
        return this.keyIdDto;
    }
    
    public void setKeyIdDto(final KeyIdDto keyIdDto) {
        this.keyIdDto = keyIdDto;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ApplicationDto)) {
            return false;
        }
        final ApplicationDto other = (ApplicationDto)o;
        if (!other.canEqual(this)) {
            return false;
        }
        final Object this$keyIdDto = this.getKeyIdDto();
        final Object other$keyIdDto = other.getKeyIdDto();
        if (this$keyIdDto == null) {
            if (other$keyIdDto == null) {
                return true;
            }
        }
        else if (this$keyIdDto.equals(other$keyIdDto)) {
            return true;
        }
        return false;
    }
    
    protected boolean canEqual(final Object other) {
        return other instanceof ApplicationDto;
    }
    
    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $keyIdDto = this.getKeyIdDto();
        result = result * 59 + (($keyIdDto == null) ? 43 : $keyIdDto.hashCode());
        return result;
    }
    
    @Override
    public String toString() {
        return "ApplicationDto(keyIdDto=" + this.getKeyIdDto() + ")";
    }
}
