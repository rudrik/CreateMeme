package com.apptechx.creatememe.model;

/**
 * Created by AppTechX on 10/16/2014.
 */
public class TemplateItem {
    private String TemplateName;
    private int TemplateImageId;
    private boolean TemplateFav;

    public String getTemplateName() {
        return TemplateName;
    }

    public void setTemplateName(String templateName) {
        TemplateName = templateName;
    }

    public int getTemplateImageId() {
        return TemplateImageId;
    }

    public void setTemplateImageId(int templateImageId) {
        TemplateImageId = templateImageId;
    }

    public boolean isTemplateFav() {
        return TemplateFav;
    }

    public void setTemplateFav(boolean templateFav) {
        TemplateFav = templateFav;
    }
}
