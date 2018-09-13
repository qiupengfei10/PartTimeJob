package com.zhitou.job.main.utils;

import android.app.Activity;
import android.util.Log;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.zhitou.job.main.been.BaseBean;
import com.zhitou.job.main.inter.ResultListener;
import com.zhitou.job.main.view.DialogCallBack;

import java.util.Iterator;
import java.util.Map;


/**
 * Created by yinyanyang on 2018/1/31.
 */

public class BaseOkGoUtils {
    private static HttpParams params;

    //记住上一次请求的链接
    private static String lastUrl;
    //上次请求是否已完成
    private static boolean lastRequestFinish = true;


    //post 无缓冲条
    public static void postOkGo(Map<String, Object> map, String url, final ResultListener listener) {
        if (lastUrl != null
                && BaseOkGoUtils.lastUrl.equals(url)
                && !lastRequestFinish){
            return;
        }
        lastUrl = url;
        lastRequestFinish = false;

        if (map != null) {
            params = mapParse(map);
            OkGo.<String>post(url)
                    .params(params)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            if (response.body() != null) {
                                BaseBean baseBean = new Gson().fromJson(response.body().toString(), BaseBean.class);
                                if (baseBean.getState() == 10000) {
                                    listener.onSucceeded(baseBean.getDatas());
                                } else {
                                    listener.onFailed(baseBean.getContent());
                                }
                            }

                            lastRequestFinish = true;
                        }

                        @Override
                        public void onError(Response<String> response) {
                            super.onError(response);
                            if (response != null) {
                                listener.onErr(response.getException().toString());
                            }

                            lastRequestFinish = true;
                        }
                    });
        }
    }

    //post 有缓冲条
    public static void postOkGo(Map<String, Object> map, String url, final Activity context, final ResultListener listener) {
        if (lastUrl != null
                && BaseOkGoUtils.lastUrl.equals(url)
                && !lastRequestFinish){
            return;
        }
        lastUrl = url;
        lastRequestFinish = false;

        if (map != null) {
            params = mapParse(map);
            OkGo.<String>post(url)
                    .params(params)
                    .execute(new DialogCallBack(context){
                        @Override
                        public void onSuccess(Response<String> response) {
                            super.onSuccess(response);
                            if (response.body() != null) {
                                BaseBean baseBean = new Gson().fromJson(response.body().toString(), BaseBean.class);
                                if (baseBean.getState() == 10000) {
                                    listener.onSucceeded(baseBean.getDatas());
                                } else {
                                    listener.onFailed(baseBean.getContent());
                                }
                            }
                            lastRequestFinish = true;
                        }

                        @Override
                        public void onError(Response<String> response) {
                            super.onError(response);
                            if (response != null) {
                                listener.onErr(response.getException().toString());
                            }

                            lastRequestFinish = true;
                        }
                    });

        }
    }


    //get 有参数 无缓冲
    public static void getOkGo(Map<String, Object> map, String url, final ResultListener listener) {
        if (map != null) {
            params = mapParse(map);
            OkGo.<String>get(url)
                    .params(params)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            if (response.body() != null) {
                                Log.e("qpf","返回的json -- " + response.body());
                                BaseBean baseBean = new Gson().fromJson(response.body().toString(), BaseBean.class);
                                if (baseBean.getState() == 10000) {
                                    listener.onSucceeded(baseBean.getDatas());
                                } else {
                                    listener.onFailed(baseBean.getContent());
                                }
                            }
                        }

                        @Override
                        public void onError(Response<String> response) {
                            super.onError(response);
                            if (response != null) {
                                listener.onErr(response.getException().toString());
                            }
                        }

                    });
        }
    }

    //get 有参数 有缓冲
    public static void getOkGo(Map<String, Object> map, String url, final Activity context, final ResultListener listener) {
        if (map != null) {
            params = mapParse(map);
            OkGo.<String>get(url)
                    .params(params)
                    .execute(new DialogCallBack(context) {
                        @Override
                        public void onSuccess(Response<String> response) {
                            super.onSuccess(response);
                            if (response.body() != null) {
                                BaseBean baseBean = new Gson().fromJson(response.body().toString(), BaseBean.class);
                                if (baseBean.getState() == 10000) {
                                    listener.onSucceeded(baseBean.getDatas());
                                } else {
                                    listener.onFailed(baseBean.getContent());
                                }
                            }
                        }

                        @Override
                        public void onError(Response<String> response) {
                            super.onError(response);
                            if (response != null) {
                                listener.onErr(response.getException().toString());
                            }

                        }
                    });
        }
    }

    //get 无参数 无缓冲
    public static void getOkGo(String url, final ResultListener listener) {
        OkGo.<String>get(url)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if (response.body() != null) {
                            Log.e("qpf","getOkGo --- " + response.body());
                            BaseBean baseBean = new Gson().fromJson(response.body().toString(), BaseBean.class);
                            if (baseBean.getState() == 10000) {
                                listener.onSucceeded(baseBean.getDatas());
                            } else {
                                listener.onFailed(baseBean.getContent());
                            }
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        if (response != null) {
                            listener.onErr(response.getException().toString());
                        }
                    }
                });
    }


    //get 无参数 有缓冲
    public static void getOkGo(String url, final Activity context, final ResultListener listener) {
        OkGo.<String>get(url)
                .execute(new DialogCallBack(context) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        super.onSuccess(response);
                        if (response.body() != null) {
                            BaseBean baseBean = new Gson().fromJson(response.body().toString(), BaseBean.class);
                            if (baseBean.getState() == 10000) {
                                listener.onSucceeded(baseBean.getDatas());
                            } else {
                                listener.onFailed(baseBean.getContent());
                            }
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        if (response != null) {
                            listener.onErr(response.getException().toString());
                        }
                    }
                });
    }

    //delete  确认参数拼接在地址值后面  无缓冲
    public static void deleteOkGo(Map<String, Object> map, String url, final ResultListener listener) {
        if (map != null) {
            params = mapParse(map);
            OkGo.<String>delete(url)
                    .isSpliceUrl(true)
                    .params(params)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            if (response.body() != null) {
                                BaseBean baseBean = new Gson().fromJson(response.body().toString(), BaseBean.class);
                                if (baseBean.getState() == 10000) {
                                    listener.onSucceeded(baseBean.getDatas());
                                } else {
                                    listener.onFailed(baseBean.getContent());
                                }
                            }
                        }

                        @Override
                        public void onError(Response<String> response) {
                            super.onError(response);
                            if (response != null) {
                                listener.onErr(response.getException().toString());
                            }
                        }
                    });
        }
    }

    //put 无缓冲  点赞用到
    public static void putOkGo(Map<String, Object> map, String url, final ResultListener listener) {
        if (lastUrl != null
                && BaseOkGoUtils.lastUrl.equals(url)
                && !lastRequestFinish){
            return;
        }
        lastUrl = url;
        lastRequestFinish = false;

        if (map != null) {
            params = mapParse(map);
            String json = new Gson().toJson(map);
            OkGo.<String>put(url)
//                    .params(params)
                    .upJson(json)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            Log.e("qpf","PUT请求 --- " + response);
                            lastRequestFinish = true;
                            if (response.body() != null) {
                                Log.e("qpf","PUT请求 --- " + response);
                                BaseBean baseBean = new Gson().fromJson(response.body().toString(), BaseBean.class);
                                if (baseBean.getState() == 10000) {
//                                    listener.onSucceeded(baseBean.getDatas());
                                    //点赞成功后返回的数据不在data中,在content中,所以这里返回baseBeen
                                    listener.onSucceeded(baseBean);
                                } else {
                                    listener.onFailed(baseBean.getContent());
                                }
                            }
                        }

                        @Override
                        public void onError(Response<String> response) {
                            super.onError(response);
                            if (response != null) {
                                listener.onErr(response.getException().toString());
                                lastRequestFinish = true;
                            }
                        }
                    });
        }
    }

    //参数里面包含集合 当做json传参  有缓冲
    public static void upJsonOkGo(Map<String, Object> map, String url, Activity context, final ResultListener listener) {
        if (lastUrl != null
                && BaseOkGoUtils.lastUrl.equals(url)
                && !lastRequestFinish){
            return;
        }
        lastUrl = url;
        lastRequestFinish = false;

        String json = new Gson().toJson(map);
        Log.e("qpf","参数 -- " + json);
        OkGo.<String>post(url)
                .upJson(json)
                .execute(new DialogCallBack(context) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        super.onSuccess(response);
                        lastRequestFinish = true;
                        if (response.body() != null) {
                            BaseBean baseBean = new Gson().fromJson(response.body().toString(), BaseBean.class);
                            if (baseBean.getState() == 10000) {
                                listener.onSucceeded(baseBean.getDatas());
                            } else {
                                listener.onFailed(baseBean.getContent());
                            }
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        lastRequestFinish = true;
                        if (response != null) {
                            listener.onErr(response.getException().toString());
                        }
                    }
                });
    }


    //将map放到params
    public static HttpParams mapParse(Map<String, Object> map) {
        HttpParams params = new HttpParams();
        Iterator<Map.Entry<String, Object>> entryIterator = map.entrySet().iterator();
        while (entryIterator.hasNext()) {
            Map.Entry<String, Object> entry = entryIterator.next();
            params.put(entry.getKey(), entry.getValue().toString());
        }
        return params;
    }




}