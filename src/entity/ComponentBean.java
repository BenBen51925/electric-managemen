package entity;

public class ComponentBean {
    private Integer sizeWidth;
    private Integer sizeHeight;
    private Integer localX;
    private Integer localY;
    private String name;
    private String componentType;

    public String getComponentType() {
        return componentType;
    }

    public void setComponentType(String componentType) {
        this.componentType = componentType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSizeWidth() {
        return sizeWidth;
    }

    public void setSizeWidth(Integer sizeWidth) {
        this.sizeWidth = sizeWidth;
    }

    public Integer getSizeHeight() {
        return sizeHeight;
    }

    public void setSizeHeight(Integer sizeHeight) {
        this.sizeHeight = sizeHeight;
    }

    public Integer getLocalX() {
        return localX;
    }

    public void setLocalX(Integer localX) {
        this.localX = localX;
    }

    public Integer getLocalY() {
        return localY;
    }

    public void setLocalY(Integer localY) {
        this.localY = localY;
    }

    @Override
    public String toString() {
        return "ComponentBean{" +
                "sizeWidth=" + sizeWidth +
                ", sizeHeight=" + sizeHeight +
                ", localX=" + localX +
                ", localY=" + localY +
                ", name='" + name + '\'' +
                ", componentType='" + componentType + '\'' +
                '}';
    }
}
