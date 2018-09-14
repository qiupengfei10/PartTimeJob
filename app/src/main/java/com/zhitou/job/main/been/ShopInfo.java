package com.zhitou.job.main.been;

import java.util.List;

/**
 * Created by LCH on 2018/8/31.
 */
public class ShopInfo {
        /**
         * Results : [{"PictUrl":"http://logo.taobaocdn.com/shop-logo/34/f3/TB1EI7fKVXXXXaIXVXX1rlbFXXX.JPG","SellerNick":"tb_8979975","ShopTitle":"潮时尚女鞋之都","ShopType":"C","ShopUrl":"http://store.taobao.com/shop/view_shop.htm?user_number_id=839232283","UserId":839232283}]
         * TotalResults : 1
         * ErrCode :
         * ErrMsg :
         * IsError : false
         * SubErrCode :
         * SubErrMsg :
         */

        private int TotalResults;
        private String ErrCode;
        private String ErrMsg;
        private boolean IsError;
        private String SubErrCode;
        private String SubErrMsg;
        private List<ResultsBean> Results;

        public int getTotalResults() {
            return TotalResults;
        }

        public void setTotalResults(int TotalResults) {
            this.TotalResults = TotalResults;
        }

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

        public List<ResultsBean> getResults() {
            return Results;
        }

        public void setResults(List<ResultsBean> Results) {
            this.Results = Results;
        }

        public static class ResultsBean {
            /**
             * PictUrl : http://logo.taobaocdn.com/shop-logo/34/f3/TB1EI7fKVXXXXaIXVXX1rlbFXXX.JPG
             * SellerNick : tb_8979975
             * ShopTitle : 潮时尚女鞋之都
             * ShopType : C
             * ShopUrl : http://store.taobao.com/shop/view_shop.htm?user_number_id=839232283
             * UserId : 839232283
             */

            private String PictUrl;
            private String SellerNick;
            private String ShopTitle;
            private String ShopType;
            private String ShopUrl;
            private long UserId;

            public String getPictUrl() {
                return PictUrl;
            }

            public void setPictUrl(String PictUrl) {
                this.PictUrl = PictUrl;
            }

            public String getSellerNick() {
                return SellerNick;
            }

            public void setSellerNick(String SellerNick) {
                this.SellerNick = SellerNick;
            }

            public String getShopTitle() {
                return ShopTitle;
            }

            public void setShopTitle(String ShopTitle) {
                this.ShopTitle = ShopTitle;
            }

            public String getShopType() {
                return ShopType;
            }

            public void setShopType(String ShopType) {
                this.ShopType = ShopType;
            }

            public String getShopUrl() {
                return ShopUrl;
            }

            public void setShopUrl(String ShopUrl) {
                this.ShopUrl = ShopUrl;
            }

            public long getUserId() {
                return UserId;
            }

            public void setUserId(long UserId) {
                this.UserId = UserId;
            }
        }
}
