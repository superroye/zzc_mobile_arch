package com.supylc.h5cache;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * 静态资源过滤器
 *
 * @author shamschu
 * @Date 2018/2/11 下午2:56
 */
public abstract class CacheFilter {

    private Set<String> urlStartRegexs;
    private Set<String> extensions;
    public static final int CACHE_NONE = 1;
    public static final int CACHE_ALL = 2;
    public static final int CACHE_FILTER = 0;

    public CacheFilter() {
        urlStartRegexs = new HashSet<>();
        extensions = new HashSet<>();

        extensions.add("mp4");
        extensions.add("mp3");
        extensions.add("ogg");
        extensions.add("avi");
        extensions.add("wmv");
        extensions.add("flv");
        extensions.add("rmvb");
        extensions.add("3gp");

        fillUrlInFilter(urlStartRegexs);
        fillExtensionInFilter(extensions);
    }

    public abstract void fillUrlInFilter(Set<String> urlStartRegexs);

    public abstract void fillExtensionInFilter(Set<String> extensions);

    public abstract int cacheMode();

    /**
     * 是否需要过滤该类型的静态资源
     *
     * @param extension 静态资源类型, png、css、js等等
     * @return true过滤/false不过滤 若过滤，则不在使用本地缓存
     */
    public boolean isFilterExtension(String extension) {
        return extensions.contains(extension);
    }

    public boolean isNeedCache(String url) {
        if (CACHE_NONE == cacheMode()) {
            return false;
        } else if (CACHE_ALL == cacheMode()) {
            return true;
        }
        Pattern pattern;
        String startUrl = url;
        int index = startUrl.indexOf("?", 1);
        if (index < 0) {
            index = startUrl.indexOf("#", 1);
        }
        if (index > -1) {
            startUrl = startUrl.substring(0, index);
        }
        for (String regx : urlStartRegexs) {
            pattern = Pattern.compile(regx);
            if (pattern.matcher(startUrl).find()) {
                return true;
            }
        }
        return false;
    }
}
