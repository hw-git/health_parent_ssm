//import cn.lanqiao.constant.MessageConstant;
//import cn.lanqiao.constant.RedisConstant;
//import cn.lanqiao.entity.Result;
//import cn.lanqiao.utils.QiniuUtils;
//import com.google.gson.Gson;
//import com.qiniu.common.QiniuException;
//import com.qiniu.common.Zone;
//import com.qiniu.http.Response;
//import com.qiniu.storage.BucketManager;
//import com.qiniu.storage.Configuration;
//import com.qiniu.storage.UploadManager;
//import com.qiniu.storage.model.DefaultPutRet;
//import com.qiniu.util.Auth;
//import org.junit.Test;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.web.multipart.MultipartFile;
//import redis.clients.jedis.JedisPool;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.util.UUID;
//
///**
// * @Author: Hou
// * @Date: 2021/5/7 14:16
// * @Description:
// */
//public class QiNiuTest {
//    //使用七牛云提供的SDK实现将本地图片上传到七牛云服务器
//    @Test
//    public void test1(){
//        //构造一个带指定Zone对象的配置类
//        Configuration cfg = new Configuration(Zone.zone2());
////...其他参数参考类注释
//        UploadManager uploadManager = new UploadManager(cfg);
////...生成上传凭证，然后准备上传
//        String accessKey = "oKCbQlrmoyne_Y3lBf44ohPkRQnEnfEyw3Ndmf9w";
//        String secretKey = "XGiIoNMixoVF5hOyUal9HVTaUlJ0SoahU0dBZsO-";
//        String bucket = "hrh-healthytest";
////如果是Windows情况下，格式是 D:\\qiniu\\test.png
//        String localFilePath = "C:\\Users\\lenovo\\Pictures\\Saved Pictures\\1.jpg";
////默认不指定key的情况下，以文件内容的hash值作为文件名
//        String key = "abc.jpg";
//        Auth auth = Auth.create(accessKey, secretKey);
//        String upToken = auth.uploadToken(bucket);
//        System.out.println(1);
//        try {
//            System.out.println(2);
//            Response response = uploadManager.put(localFilePath, key, upToken);
//            System.out.println(3);
//            //解析上传成功的结果
//            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
//            System.out.println(putRet.key);
//            System.out.println("------");
//            System.out.println(putRet.hash);
//        } catch (QiniuException ex) {
//            Response r = ex.response;
//            System.err.println("---" + r.toString());
//            try {
//                System.err.println("+++" + r.bodyString());
//            } catch (QiniuException ex2) {
//                //ignore
//            }
//        }
//    }
//
//    //删除七牛云服务器中的图片
//    @Test
//    public void test2(){
//        //构造一个带指定Zone对象的配置类
//        Configuration cfg = new Configuration(Zone.zone0());
//        //...其他参数参考类注释
//        String accessKey = "oKCbQlrmoyne_Y3lBf44ohPkRQnEnfEyw3Ndmf9w";
//        String secretKey = "XGiIoNMixoVF5hOyUal9HVTaUlJ0SoahU0dBZsO-";
//        String bucket = "hrh-healthytest";
//        String key = "abc.jpg";
//        Auth auth = Auth.create(accessKey, secretKey);
//        BucketManager bucketManager = new BucketManager(auth, cfg);
//        try {
//            bucketManager.delete(bucket, key);
//        } catch (QiniuException ex) {
//            //如果遇到异常，说明删除失败
//            System.err.println(ex.code());
//            System.err.println(ex.response.toString());
//        }
//    }
////    @Test
////    public void test3() throws IOException {
////        JedisPool jedisPool = new JedisPool();
////        String originalFilename = "H]38QF2[GK3NCT8N6%UU{BC.jpg";//原始文件名 3bd90d2c-4e82-42a1-a401-882c88b06a1a2.jpg
////        int index = 23;
////        String extention = "C.jpg";//.jpg
////        String fileName = "37e6e7d2-94da-4498-846b-392dc3efc241C.jpg";//	FuM1Sa5TtL_ekLsdkYWcf5pyjKGu.jpg
////        //将文件上传到七牛云服务器
////        QiniuUtils.upload2Qiniu("org.springframework.web.multipart.commons.CommonsMultipartFile@45079a01".getBytes(),fileName);
////        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES,fileName);
////    }
//}
