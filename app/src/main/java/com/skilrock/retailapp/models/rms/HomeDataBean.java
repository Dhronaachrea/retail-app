package com.skilrock.retailapp.models.rms;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class HomeDataBean implements Parcelable {

    @SerializedName("responseCode")
    @Expose
    private Integer responseCode;

    @SerializedName("responseMessage")
    @Expose
    private String responseMessage;

    @SerializedName("responseData")
    @Expose
    private ResponseData responseData;

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public ResponseData getResponseData() {
        return responseData;
    }

    public void setResponseData(ResponseData responseData) {
        this.responseData = responseData;
    }

    @NonNull
    @Override
    public String toString() {
        return "HomeDataBean{" + "responseCode=" + responseCode + ", responseMessage='" + responseMessage + '\'' + ", responseData=" + responseData + '}';
    }

    public static class ResponseData implements Parcelable {

        @SerializedName("moduleBeanLst")
        @Expose
        private ArrayList<ModuleBeanList> moduleBeanLst = null;

        @SerializedName("statusCode")
        @Expose
        private Integer statusCode;

        @SerializedName("message")
        @Expose
        private String message;

        public ArrayList<ModuleBeanList> getModuleBeanLists() {
            return moduleBeanLst;
        }

        public void setModuleBeanList(ArrayList<ModuleBeanList> moduleBeanLst) {
            this.moduleBeanLst = moduleBeanLst;
        }

        public Integer getStatusCode() {
            return statusCode;
        }

        public void setStatusCode(Integer statusCode) {
            this.statusCode = statusCode;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        @NonNull
        @Override
        public String toString() {
            return "ResponseData{" + "moduleBeanList=" + moduleBeanLst + ", statusCode=" + statusCode + ", message='" + message + '\'' + '}';
        }

        public static class ModuleBeanList implements Parcelable {

            @SerializedName("moduleId")
            @Expose
            private Integer moduleId;

            @SerializedName("moduleCode")
            @Expose
            private String moduleCode;

            @SerializedName("sequence")
            @Expose
            private Integer sequence;

            @SerializedName("displayName")
            @Expose
            private String displayName;

            @SerializedName("menuBeanList")
            @Expose
            private ArrayList<MenuBeanList> menuBeanList = null;

            public Integer getModuleId() {
                return moduleId;
            }

            public void setModuleId(Integer moduleId) {
                this.moduleId = moduleId;
            }

            public String getModuleCode() {
                return moduleCode;
            }

            public void setModuleCode(String moduleCode) {
                this.moduleCode = moduleCode;
            }

            public Integer getSequence() {
                return sequence;
            }

            public void setSequence(Integer sequence) {
                this.sequence = sequence;
            }

            public String getDisplayName() {
                return displayName;
            }

            public void setDisplayName(String displayName) {
                this.displayName = displayName;
            }

            public ArrayList<MenuBeanList> getMenuBeanList() {
                return menuBeanList;
            }

            public void setMenuBeanList(ArrayList<MenuBeanList> menuBeanList) {
                this.menuBeanList = menuBeanList;
            }

            @NonNull
            @Override
            public String toString() {
                return "ModuleBeanLst{" + "moduleId=" + moduleId + ", moduleCode='" + moduleCode + '\'' + ", sequence=" + sequence + ", displayName='" + displayName + '\'' + ", menuBeanList=" + menuBeanList + '}';
            }

            public static class MenuBeanList implements Parcelable {

                @SerializedName("menuId")
                @Expose
                private Integer menuId;

                @SerializedName("menuCode")
                @Expose
                private String menuCode;

                @SerializedName("caption")
                @Expose
                private String caption;

                @SerializedName("sequence")
                @Expose
                private Integer sequence;

                @SerializedName("checkForPermissions")
                @Expose
                private Integer checkForPermissions;

                @SerializedName("basePath")
                @Expose
                private String basePath;

                @SerializedName("relativePath")
                @Expose
                private String relativePath;

                @SerializedName("apiDetails")
                @Expose
                private String apiDetails;

                @SerializedName("permissionCodeList")
                @Expose
                private ArrayList<String> permissionCodeList = null;

                public Integer getMenuId() {
                    return menuId;
                }

                public void setMenuId(Integer menuId) {
                    this.menuId = menuId;
                }

                public String getMenuCode() {
                    return menuCode;
                }

                public void setMenuCode(String menuCode) {
                    this.menuCode = menuCode;
                }

                public String getCaption() {
                    return caption;
                }

                public void setCaption(String caption) {
                    this.caption = caption;
                }

                public Integer getSequence() {
                    return sequence;
                }

                public void setSequence(Integer sequence) {
                    this.sequence = sequence;
                }

                public Integer getCheckForPermissions() {
                    return checkForPermissions;
                }

                public void setCheckForPermissions(Integer checkForPermissions) {
                    this.checkForPermissions = checkForPermissions;
                }

                public String getBasePath() {
                    return basePath;
                }

                public void setBasePath(String basePath) {
                    this.basePath = basePath;
                }

                public String getRelativePath() {
                    return relativePath;
                }

                public void setRelativePath(String relativePath) {
                    this.relativePath = relativePath;
                }

                public String getApiDetails() {
                    return apiDetails;
                }

                public void setApiDetails(String apiDetails) {
                    this.apiDetails = apiDetails;
                }

                public ArrayList<String> getPermissionCodeList() {
                    return permissionCodeList;
                }

                public void setPermissionCodeList(ArrayList<String> permissionCodeList) {
                    this.permissionCodeList = permissionCodeList;
                }

                @NonNull
                @Override
                public String toString() {
                    return "MenuBeanList{" + "menuId=" + menuId + ", menuCode='" + menuCode + '\'' + ", caption='" + caption + '\'' + ", sequence=" + sequence + ", checkForPermissions=" + checkForPermissions + ", basePath='" + basePath + '\'' + ", relativePath='" + relativePath + '\'' + ", apiDetails='" + apiDetails + '\'' + ", permissionCodeList=" + permissionCodeList + '}';
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeValue(this.menuId);
                    dest.writeString(this.menuCode);
                    dest.writeString(this.caption);
                    dest.writeValue(this.sequence);
                    dest.writeValue(this.checkForPermissions);
                    dest.writeString(this.basePath);
                    dest.writeString(this.relativePath);
                    dest.writeString(this.apiDetails);
                    dest.writeStringList(this.permissionCodeList);
                }

                public MenuBeanList() {
                }

                protected MenuBeanList(Parcel in) {
                    this.menuId = (Integer) in.readValue(Integer.class.getClassLoader());
                    this.menuCode = in.readString();
                    this.caption = in.readString();
                    this.sequence = (Integer) in.readValue(Integer.class.getClassLoader());
                    this.checkForPermissions = (Integer) in.readValue(Integer.class.getClassLoader());
                    this.basePath = in.readString();
                    this.relativePath = in.readString();
                    this.apiDetails = in.readString();
                    this.permissionCodeList = in.createStringArrayList();
                }

                public static final Parcelable.Creator<MenuBeanList> CREATOR = new Parcelable.Creator<MenuBeanList>() {
                    @Override
                    public MenuBeanList createFromParcel(Parcel source) {
                        return new MenuBeanList(source);
                    }

                    @Override
                    public MenuBeanList[] newArray(int size) {
                        return new MenuBeanList[size];
                    }
                };
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeValue(this.moduleId);
                dest.writeString(this.moduleCode);
                dest.writeValue(this.sequence);
                dest.writeString(this.displayName);
                dest.writeTypedList(this.menuBeanList);
            }

            public ModuleBeanList() {
            }

            protected ModuleBeanList(Parcel in) {
                this.moduleId = (Integer) in.readValue(Integer.class.getClassLoader());
                this.moduleCode = in.readString();
                this.sequence = (Integer) in.readValue(Integer.class.getClassLoader());
                this.displayName = in.readString();
                this.menuBeanList = in.createTypedArrayList(MenuBeanList.CREATOR);
            }

            public static final Parcelable.Creator<ModuleBeanList> CREATOR = new Parcelable.Creator<ModuleBeanList>() {
                @Override
                public ModuleBeanList createFromParcel(Parcel source) {
                    return new ModuleBeanList(source);
                }

                @Override
                public ModuleBeanList[] newArray(int size) {
                    return new ModuleBeanList[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeTypedList(this.moduleBeanLst);
            dest.writeValue(this.statusCode);
            dest.writeString(this.message);
        }

        public ResponseData() {
        }

        protected ResponseData(Parcel in) {
            this.moduleBeanLst = in.createTypedArrayList(ModuleBeanList.CREATOR);
            this.statusCode = (Integer) in.readValue(Integer.class.getClassLoader());
            this.message = in.readString();
        }

        public static final Parcelable.Creator<ResponseData> CREATOR = new Parcelable.Creator<ResponseData>() {
            @Override
            public ResponseData createFromParcel(Parcel source) {
                return new ResponseData(source);
            }

            @Override
            public ResponseData[] newArray(int size) {
                return new ResponseData[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.responseCode);
        dest.writeString(this.responseMessage);
        dest.writeParcelable(this.responseData, flags);
    }

    public HomeDataBean() {
    }

    protected HomeDataBean(Parcel in) {
        this.responseCode = (Integer) in.readValue(Integer.class.getClassLoader());
        this.responseMessage = in.readString();
        this.responseData = in.readParcelable(ResponseData.class.getClassLoader());
    }

    public static final Parcelable.Creator<HomeDataBean> CREATOR = new Parcelable.Creator<HomeDataBean>() {
        @Override
        public HomeDataBean createFromParcel(Parcel source) {
            return new HomeDataBean(source);
        }

        @Override
        public HomeDataBean[] newArray(int size) {
            return new HomeDataBean[size];
        }
    };
}
