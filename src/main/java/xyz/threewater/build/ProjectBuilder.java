package xyz.threewater.build;

import xyz.threewater.exception.BuildFailedException;

    /**
     * ProjectBuilder接口，可以编译项目，也可以编译单个源码文件
     */
    public interface ProjectBuilder {

        /**
         *
         * @param sourceFile 源文件路径
         * @param outPutPath 指定生成的目录 如果为空则默认和源代码文件目录相同
         * @return 生成的编译后文件名称
         */
        String buildSourceFile(String sourceFile,String outPutPath) throws BuildFailedException;

        /**
         * 编译当前项目，所有环境都包含在了ProjectEnv中
         */
        void buildProject();
    }
