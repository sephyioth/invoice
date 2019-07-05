package com.hackill;

import java.io.File;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class GreenDao {


    final static String PACKAGE_DB = "com.genesis.ginvoice.db";
    final static int    VERSION    = 2;

//    public static void main(String[] args) throws Exception {
//        // 正如你所见的，你创建了一个用于添加实体（Entity）的模式（Schema）对象。
//        // 两个参数分别代表：数据库版本号与自动生成代码的包路径。
//        Schema schema = new Schema(VERSION, PACKAGE_DB);
////        当然，如果你愿意，你也可以分别指定生成的 Bean 与 DAO 类所在的目录，只要如下所示：
////        Schema schema = new Schema(1, "cn.ginshell.bong.db");
////        schema.setDefaultJavaPackageDao("cn.ginshell.bong.db.dao");
//
////        模式（Schema）同时也拥有两个默认的 flags，分别用来标示 entity 是否是 active 以及是否使用 keep sections。
////        schema.enableActiveEntitiesByDefault();
////        schema.enableKeepSectionsByDefault();
//
//        // 一旦你拥有了一个 Schema 对象后，你便可以使用它添加实体（Entities）了。
//        addHealCity(schema);
//
//        addEntity2(schema);
//
//        // 最后我们将使用 DAOGenerator 类的 generateAll() 方法自动生成代码，此处你需要根据自己的情况更改输出目录（既之前创建的 java-gen)。
//        // 其实，输出目录的路径可以在 build.gradle 中设置，有兴趣的朋友可以自行搜索，这里就不再详解。
//        String outDir = Utils.getAbsolutePath() + File.separator + "app/src/main/java";
//        System.out.println("outDir = " + outDir);
//        new DaoGenerator().generateAll(schema, outDir);
//    }


    private static void addCardDocument (Schema schema) {
        Entity card = schema.addEntity("Card");
        card.addLongProperty("id").primaryKey();
        card.addIntProperty("num");
        card.addFloatProperty("price");
        card.addStringProperty("introduction");
        card.addStringProperty("time");
        card.addStringProperty("doc_path");
    }

    private static void addInvoice (Schema schema) {
        Entity invoice = schema.addEntity("Invoice");
        invoice.addLongProperty("id").primaryKey();
        invoice.addLongProperty("card_id").primaryKeyDesc();
        invoice.addStringProperty("photo_file_path");
        invoice.addStringProperty("small_photo_path");
        invoice.addFloatProperty("price");
        invoice.addStringProperty("sellerName");
        invoice.addStringProperty("sellerBank");
        invoice.addStringProperty("sellerAddress");
        invoice.addStringProperty("purchaserName");
        invoice.addStringProperty("purchaserAddress");
        invoice.addStringProperty("purchaserRegisterNum");
        invoice.addStringProperty("amountInWords");
        invoice.addFloatProperty("amountInFiguers");
        invoice.addStringProperty("InvoiceNum");
        invoice.addStringProperty("checker");

        invoice.addDateProperty("create_time");
    }

    private static void addPerson (Schema schema) {
        Entity entity = schema.addEntity("PersonalInfo");
        entity.addLongProperty("id").primaryKey();
        entity.addStringProperty("name");
        entity.addStringProperty("company");
        entity.addStringProperty("phone");
        entity.addStringProperty("bankId");
        entity.addStringProperty("bankAddr");
        entity.addStringProperty("email");

    }

    public static void main (String[] args) throws Exception {
        Schema schema = new Schema(VERSION, PACKAGE_DB);
        addCardDocument(schema);
        addInvoice(schema);
        addPerson(schema);
        String outDir = Utils.getAbsolutePath() + File.separator + "app/src/main/java";
        new DaoGenerator().generateAll(schema, outDir);

    }
}
