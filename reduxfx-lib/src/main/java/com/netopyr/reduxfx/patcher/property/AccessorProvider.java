package com.netopyr.reduxfx.patcher.property;

public interface AccessorProvider {

    <TYPE, ACTION> Accessor<TYPE, ACTION> create(PropertyKey propertyKey);

}
