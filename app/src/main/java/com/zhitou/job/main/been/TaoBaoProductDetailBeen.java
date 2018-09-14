package com.zhitou.job.main.been;

import java.util.List;

public class TaoBaoProductDetailBeen {

        /**
         * ErrCode :
         * ErrMsg :
         * IsError : false
         * SubErrCode :
         * SubErrMsg :
         * Detail : {"CatLeafName":"现代装饰画","CatName":"家居饰品","FreeShipment":false,"HGoodRate":false,"HPayRate30":false,"IRfdRate":false,"IsPrepay":false,"ItemUrl":"https://item.taobao.com/item.htm?id=555396561895","Nick":"第五空间画廊","NumIid":555396561895,"PictUrl":"https://img.alicdn.com/bao/uploaded/i3/326238586/TB2OgsKIGmWBuNjy1XaXXXCbXXa_!!326238586.jpg","Provcity":"浙江 杭州","Ratesum":0,"ReservePrice":"179","SellerId":326238586,"ShopDsr":0,"SmallImages":["https://img.alicdn.com/i3/326238586/TB28xtOAwKTBuNkSne1XXaJoXXa_!!326238586.jpg","https://img.alicdn.com/i2/326238586/TB2YGpNIKOSBuNjy0FdXXbDnVXa_!!326238586.jpg","https://img.alicdn.com/i4/326238586/TB275RcIFGWBuNjy0FbXXb4sXXa_!!326238586.jpg","https://img.alicdn.com/i2/326238586/TB25lNMxZuYBuNkSmRyXXcA3pXa_!!326238586.jpg"],"Title":"美式抽象装饰画 客厅挂画简美壁画玄关欧式沙发背景墙画海洋之心","UserType":0,"Volume":15,"ZkFinalPrice":"125.3"}
         */

        private String ErrCode;
        private String ErrMsg;
        private boolean IsError;
        private String SubErrCode;
        private String SubErrMsg;
        private DetailBean Detail;

        public String getErrCode() {
            return ErrCode;
        }

        public void setErrCode(String ErrCode) {
            this.ErrCode = ErrCode;
        }

        public String getErrMsg() {
            return ErrMsg;
        }

        public void setErrMsg(String ErrMsg) {
            this.ErrMsg = ErrMsg;
        }

        public boolean isIsError() {
            return IsError;
        }

        public void setIsError(boolean IsError) {
            this.IsError = IsError;
        }

        public String getSubErrCode() {
            return SubErrCode;
        }

        public void setSubErrCode(String SubErrCode) {
            this.SubErrCode = SubErrCode;
        }

        public String getSubErrMsg() {
            return SubErrMsg;
        }

        public void setSubErrMsg(String SubErrMsg) {
            this.SubErrMsg = SubErrMsg;
        }

        public DetailBean getDetail() {
            return Detail;
        }

        public void setDetail(DetailBean Detail) {
            this.Detail = Detail;
        }

        public static class DetailBean {
            /**
             * CatLeafName : 现代装饰画
             * CatName : 家居饰品
             * FreeShipment : false
             * HGoodRate : false
             * HPayRate30 : false
             * IRfdRate : false
             * IsPrepay : false
             * ItemUrl : https://item.taobao.com/item.htm?id=555396561895
             * Nick : 第五空间画廊
             * NumIid : 555396561895
             * PictUrl : https://img.alicdn.com/bao/uploaded/i3/326238586/TB2OgsKIGmWBuNjy1XaXXXCbXXa_!!326238586.jpg
             * Provcity : 浙江 杭州
             * Ratesum : 0
             * ReservePrice : 179
             * SellerId : 326238586
             * ShopDsr : 0
             * SmallImages : ["https://img.alicdn.com/i3/326238586/TB28xtOAwKTBuNkSne1XXaJoXXa_!!326238586.jpg","https://img.alicdn.com/i2/326238586/TB2YGpNIKOSBuNjy0FdXXbDnVXa_!!326238586.jpg","https://img.alicdn.com/i4/326238586/TB275RcIFGWBuNjy0FbXXb4sXXa_!!326238586.jpg","https://img.alicdn.com/i2/326238586/TB25lNMxZuYBuNkSmRyXXcA3pXa_!!326238586.jpg"]
             * Title : 美式抽象装饰画 客厅挂画简美壁画玄关欧式沙发背景墙画海洋之心
             * UserType : 0
             * Volume : 15
             * ZkFinalPrice : 125.3
             */

            private String CatLeafName;
            private String CatName;
            private boolean FreeShipment;
            private boolean HGoodRate;
            private boolean HPayRate30;
            private boolean IRfdRate;
            private boolean IsPrepay;
            private String ItemUrl;
            private String Nick;
            private long NumIid;
            private String PictUrl;
            private String Provcity;
            private int Ratesum;
            private String ReservePrice;
            private long SellerId;
            private int ShopDsr;
            private String Title;
            private int UserType;
            private int Volume;
            private String ZkFinalPrice;
            private List<String> SmallImages;

            public String getCatLeafName() {
                return CatLeafName;
            }

            public void setCatLeafName(String CatLeafName) {
                this.CatLeafName = CatLeafName;
            }

            public String getCatName() {
                return CatName;
            }

            public void setCatName(String CatName) {
                this.CatName = CatName;
            }

            public boolean isFreeShipment() {
                return FreeShipment;
            }

            public void setFreeShipment(boolean FreeShipment) {
                this.FreeShipment = FreeShipment;
            }

            public boolean isHGoodRate() {
                return HGoodRate;
            }

            public void setHGoodRate(boolean HGoodRate) {
                this.HGoodRate = HGoodRate;
            }

            public boolean isHPayRate30() {
                return HPayRate30;
            }

            public void setHPayRate30(boolean HPayRate30) {
                this.HPayRate30 = HPayRate30;
            }

            public boolean isIRfdRate() {
                return IRfdRate;
            }

            public void setIRfdRate(boolean IRfdRate) {
                this.IRfdRate = IRfdRate;
            }

            public boolean isIsPrepay() {
                return IsPrepay;
            }

            public void setIsPrepay(boolean IsPrepay) {
                this.IsPrepay = IsPrepay;
            }

            public String getItemUrl() {
                return ItemUrl;
            }

            public void setItemUrl(String ItemUrl) {
                this.ItemUrl = ItemUrl;
            }

            public String getNick() {
                return Nick;
            }

            public void setNick(String Nick) {
                this.Nick = Nick;
            }

            public long getNumIid() {
                return NumIid;
            }

            public void setNumIid(long NumIid) {
                this.NumIid = NumIid;
            }

            public String getPictUrl() {
                return PictUrl;
            }

            public void setPictUrl(String PictUrl) {
                this.PictUrl = PictUrl;
            }

            public String getProvcity() {
                return Provcity;
            }

            public void setProvcity(String Provcity) {
                this.Provcity = Provcity;
            }

            public int getRatesum() {
                return Ratesum;
            }

            public void setRatesum(int Ratesum) {
                this.Ratesum = Ratesum;
            }

            public String getReservePrice() {
                return ReservePrice;
            }

            public void setReservePrice(String ReservePrice) {
                this.ReservePrice = ReservePrice;
            }

            public long getSellerId() {
                return SellerId;
            }

            public void setSellerId(long SellerId) {
                this.SellerId = SellerId;
            }

            public int getShopDsr() {
                return ShopDsr;
            }

            public void setShopDsr(int ShopDsr) {
                this.ShopDsr = ShopDsr;
            }

            public String getTitle() {
                return Title;
            }

            public void setTitle(String Title) {
                this.Title = Title;
            }

            public int getUserType() {
                return UserType;
            }

            public void setUserType(int UserType) {
                this.UserType = UserType;
            }

            public int getVolume() {
                return Volume;
            }

            public void setVolume(int Volume) {
                this.Volume = Volume;
            }

            public String getZkFinalPrice() {
                return ZkFinalPrice;
            }

            public void setZkFinalPrice(String ZkFinalPrice) {
                this.ZkFinalPrice = ZkFinalPrice;
            }

            public List<String> getSmallImages() {
                return SmallImages;
            }

            public void setSmallImages(List<String> SmallImages) {
                this.SmallImages = SmallImages;
            }
        }
}
