package com.hx.steven.app;

/**
 * 构建app模块初始化
 */
public class AppInitBuilder {
    private final boolean isInitLogger;
    private final boolean isInitX5;
    private final boolean isInitWXSDK;
    private final boolean isInitBugly;
    private final boolean isInitJpush;

    private AppInitBuilder(Builder builder) {
        this.isInitLogger = builder.isInitLogger;
        this.isInitX5 = builder.isInitX5;
        this.isInitWXSDK = builder.isInitWXSDK;
        this.isInitBugly = builder.isInitBugly;
        this.isInitJpush = builder.isInitJpush;
    }
    public static class Builder{
        private boolean isInitLogger;
        private boolean isInitX5;
        private boolean isInitWXSDK;
        private boolean isInitBugly;
        private boolean isInitJpush;

        public Builder setInitLogger(boolean initLogger) {
            this.isInitLogger = initLogger;
            return this;
        }

        public Builder setInitX5(boolean isInitX5) {
            this.isInitX5 = isInitX5;
            return this;
        }

        public Builder setInitWXSdk(boolean isInitWXSDK) {
            this.isInitWXSDK = isInitWXSDK;
            return this;
        }

        public Builder setInitBugly(boolean isInitBugly) {
            this.isInitBugly = isInitBugly;
            return this;
        }

        public Builder setInitJpush(boolean isInitJpush) {
            this.isInitJpush = isInitJpush;
            return this;
        }

        public AppInitBuilder build(){
            return new AppInitBuilder(this);
        }
    }

    public boolean isInitLogger() {
        return isInitLogger;
    }

    public boolean isInitX5() {
        return isInitX5;
    }

    public boolean isInitWXSDK() {
        return isInitWXSDK;
    }

    public boolean isInitBugly() {
        return isInitBugly;
    }

    public boolean isInitJpush() {
        return isInitJpush;
    }
}
