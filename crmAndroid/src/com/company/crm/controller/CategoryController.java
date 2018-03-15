package com.company.crm.controller;

import java.net.URI;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriTemplate;

import com.company.crm.model.Category;
import com.company.crm.service.CategoryService;
import com.company.crm.util.DBConnection;
import com.company.crm.util.Page;

import sun.misc.BASE64Encoder;

@Controller
@SuppressWarnings({ "unchecked", "rawtypes" })
public class CategoryController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger((Class)CategoryController.class);
    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value={"/addcategory"}, method={RequestMethod.GET})
    public String category(Model model) {
        List categoryList = this.categoryService.getCategoryList();
        model.addAttribute("categoryList", (Object)categoryList);
        model.addAttribute("category", (Object)new Category());
        return "addcategory";
    }

    @RequestMapping(value = "/jqgrid/addcategory", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Page<Category> listCategory(
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@RequestParam(value = "max", required = false, defaultValue = "10") int max,
			@RequestParam(value = "sort", required = false, defaultValue = "categoryId") String sort,
			@RequestParam(value = "order", required = false, defaultValue = "asc") String order) {
		List<Category> categoryList = categoryService.getSortedCategoryList(sort, order);
		final int startIdx = (page - 1) * max;
		final int endIdx = Math.min(startIdx + max, categoryList.size());
		return new Page<Category>(categoryList.subList(startIdx, endIdx), page,
				max, categoryList.size());

	}

    @RequestMapping(value={"/jqgrid/addcategory"}, method={RequestMethod.POST}, produces={"application/json"}, consumes={"application/json"})
    public ResponseEntity<String> addCategory(HttpServletRequest request, @RequestBody Category category) {
       // User user = (User)request.getSession().getAttribute("user");
        this.categoryService.addCategory(category);
        URI uri = new UriTemplate("{requestUrl}/{id}").expand(new Object[]{request.getRequestURL().toString(), 2004});
        HttpHeaders headers = new HttpHeaders();
        headers.put("Location", Collections.singletonList(uri.toASCIIString()));
        return new ResponseEntity((MultiValueMap)headers, HttpStatus.CREATED);
    }

    @RequestMapping(value={"/addcategory"}, method={RequestMethod.POST})
    public String addCategory(@Valid @ModelAttribute Category category, BindingResult result, Model model, HttpServletRequest request) {
        if (!result.hasErrors()) {
           // User user = (User)request.getSession().getAttribute("user");
            this.categoryService.addCategory(category);
            List categoryList = this.categoryService.getCategoryList();
            model.addAttribute("category", (Object)new Category());
            model.addAttribute("categoryList", (Object)categoryList);
            model.addAttribute("iscategoryAdded", (Object)true);
            return "home";
        }
        List categoryList = this.categoryService.getCategoryList();
        model.addAttribute("categoryList", (Object)categoryList);
        return "addcategory";
    }

    @RequestMapping(value={"/categorylist"}, method={RequestMethod.GET})
    public String resourceList(Model model) {
        model.addAttribute("category", (Object)new Category());
        return "categorylist";
    }

//    @RequestMapping(value={"/restcategorylist"}, method={RequestMethod.GET})
//    @ResponseBody
//    public List<Category> getAllCategory() {
//        LOGGER.debug("Start categoryList.");
//        List categoryList = this.categoryService.getCategoryList();
//        return categoryList;
//    }

    @RequestMapping(value={"/restcategoryslist/{userId}/{lang}"}, method={RequestMethod.GET}, produces={"application/json"})
    @ResponseBody
    public List<Category> getAllCategorys(@PathVariable(value="userId") int userId, @PathVariable(value="lang") int lang) {
        LOGGER.debug("Start categoryList.");
        ArrayList<Category> categoryListEnc = new ArrayList<Category>();
        int noOfMsgPerCat = 0;
        String noOfMsgPerCatenc = "0";
        String imgForCat = "";
        Connection conn = null;
        try {
        	conn =  DBConnection.getConnection();
            String sql = "select * from category";           
           // AES256JNCryptor cryptor = new AES256JNCryptor();
            BASE64Encoder base64encoder = new BASE64Encoder();
            //String password = "aes123";
            Statement stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery(sql);
//            Sudd Base Encoder
            String oneArg=base64encoder.encode("0".getBytes());
            String twoArg=base64encoder.encode("All Communications".getBytes());
            String thirdArg=base64encoder.encode("img_allCommunication_1457531396936.png".getBytes());
            String fourth=base64encoder.encode("0".getBytes());
            if (lang == 1) {
                categoryListEnc.add(new Category(new String(oneArg), new String(twoArg), "", new String(thirdArg), new String(fourth), 0));
            } else {
                categoryListEnc.add(new Category(new String(oneArg), new String(twoArg), "", new String(thirdArg), new String(fourth), 0));
            }
            while (resultSet.next()) {
                int noOfUnreadMsg = 0;
                int categoryId = resultSet.getInt("CATEGORYID");
                String firstArg=base64encoder.encode(String.valueOf(resultSet.getString("CATEGORYNAME")).getBytes());
                String secondArg=base64encoder.encode(String.valueOf(resultSet.getString("CATEGORY_NAME_BHASA")).getBytes());
                String categoryName = lang == 1 ? new String(firstArg) : new String(secondArg);
                String categoryidArg=base64encoder.encode(String.valueOf(categoryId).getBytes());
                String enc1 = new String(categoryidArg);
                String sqlPostPerCat="";
                if(lang==1){
                 sqlPostPerCat = "select count (DISTINCT md.MESSAGE_ID) from message_details md, MESSAGE_ZONE mz, MESSAGE_CHANNEL mc,MESSAGE m,category c where  c.CATEGORYID=" + categoryId + " and m.MESSAGE_ID = md.MESSAGE_ID" + " and md.message_id IN(select message_id from message where created_at<SYSDATE and EXPIRY_AT>=SYSDATE and category_id=" + categoryId + " and MSG_STATUS=1 and IS_DELETED=0)" + " and md.MESSAGE_ID=mz.MESSAGE_ID and mz.ZONE_ID IN (Select USER_ZONE.ZONE_ID from USER_ZONE where USER_ZONE.USER_ID=" + userId + ")" + " and md.MESSAGE_ID=mc.MESSAGE_ID and mc.CHANNEL_ID IN (Select USER_CHANNEL.CHANNEL_ID from USER_CHANNEL where USER_CHANNEL.USER_ID=" + userId + ")" + " and md.MESSAGE_LANG=1";
                }else
                	sqlPostPerCat = "select count (DISTINCT md.MESSAGE_ID) from message_details md, MESSAGE_ZONE mz, MESSAGE_CHANNEL mc,MESSAGE m,category c where  c.CATEGORYID=" + categoryId + " and m.MESSAGE_ID = md.MESSAGE_ID" + " and md.message_id IN(select message_id from message where created_at<SYSDATE and EXPIRY_AT>=SYSDATE and category_id=" + categoryId + " and MSG_STATUS=1 and IS_DELETED=0)" + " and md.MESSAGE_ID=mz.MESSAGE_ID and mz.ZONE_ID IN (Select USER_ZONE.ZONE_ID from USER_ZONE where USER_ZONE.USER_ID=" + userId + ")" + " and md.MESSAGE_ID=mc.MESSAGE_ID and mc.CHANNEL_ID IN (Select USER_CHANNEL.CHANNEL_ID from USER_CHANNEL where USER_CHANNEL.USER_ID=" + userId + ")" + " and md.MESSAGE_LANG=0";
                Statement stmtPostPerCat = conn.createStatement();
                ResultSet resultSetPostPerCat = stmtPostPerCat.executeQuery(sqlPostPerCat);
                if (resultSetPostPerCat != null && resultSetPostPerCat.next()) {
                    do {
                        imgForCat = resultSet.getString("CATEGORY_IMAGE");
                        noOfMsgPerCat = resultSetPostPerCat.getInt(1);
                        if (categoryId == 6) continue;
                        String categaryidcheck=base64encoder.encode(String.valueOf(resultSetPostPerCat.getInt(1)).getBytes());
                        noOfMsgPerCatenc = new String(categaryidcheck);
                        //Sudd Added
//                        if (noOfMsgPerCat <= 0) continue;
//                        String sqlGetFile = "select  md.MESSAGE_IMG_LINK from message_details md ,MESSAGE_ZONE mz, MESSAGE_CHANNEL mc where md.message_id IN(select max(message_id) from message where created_at<SYSDATE and EXPIRY_AT>=SYSDATE and category_id=" + categoryId + " and md.MESSAGE_ID=mz.MESSAGE_ID and mz.ZONE_ID IN (Select USER_ZONE.ZONE_ID from USER_ZONE where USER_ZONE.USER_ID=" + userId + ")" + " and md.MESSAGE_ID=mc.MESSAGE_ID and mc.CHANNEL_ID IN (Select USER_CHANNEL.CHANNEL_ID from USER_CHANNEL where USER_CHANNEL.USER_ID=" + userId + ")" + " and message.MSG_STATUS=1 and message.IS_DELETED=0)and md.MESSAGE_LANG=1  and md.MESSAGE_IMG_LINK IS NOT NULL";
//                        Statement stmtGetFile = conn.createStatement();
//                        ResultSet resultSetGetFile = stmtGetFile.executeQuery(sqlGetFile);
//                        if (resultSetGetFile == null || !resultSetGetFile.next()) continue;
//                        do {
//                            imgForCat = resultSetGetFile.getString("MESSAGE_IMG_LINK");
//                        } while (resultSetGetFile.next());
                        //Sudd Added
                    } while (resultSetPostPerCat.next());
                    int readMsg = 0;
                    String sqlGetreadMsg = "select count(unique rr.MESSAGE_ID) from read_reciept rr, message m where rr.USER_ID=" + userId + " and rr.MESSAGE_ID=m.MESSAGE_ID and m.CATEGORY_ID=" + categoryId;
                    Statement stmtGetreadMsg = conn.createStatement();
                    ResultSet resultSetGetReadMsg = stmtGetreadMsg.executeQuery(sqlGetreadMsg);
                    while (resultSetGetReadMsg.next()) {
                        readMsg = resultSetGetReadMsg.getInt(1);
                    }
                    if (noOfMsgPerCat > readMsg && noOfMsgPerCat != 0) {
                        noOfUnreadMsg = noOfMsgPerCat - readMsg;
                    }
                }
                String imgforenc=base64encoder.encode(imgForCat.getBytes());
                String noOfUnreadmsgenc=base64encoder.encode(String.valueOf(noOfUnreadMsg).getBytes());
                new Category().setCategoryId(Integer.valueOf(noOfUnreadMsg));
                categoryListEnc.add(new Category(enc1, categoryName, noOfMsgPerCatenc, new String(imgforenc), new String(noOfUnreadmsgenc), noOfUnreadMsg));
            }
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }finally{
			DBConnection.closeConnection(conn);
		}
        return categoryListEnc;
    }

    


//Sudd Added For restcategorylistAndro
@RequestMapping(value={"/restcategorylistAndro"}, method={RequestMethod.GET})
    @ResponseBody
    public List<Category> getAllCategoryAndro() {
        LOGGER.debug("Start categoryList.");
        List categoryList = this.categoryService.getCategoryList();
        return categoryList;
    }

    
	@RequestMapping(value={"/restcategoryslistAndro/{userId}/{lang}"}, method={RequestMethod.GET}, produces={"application/json"})
    @ResponseBody
    public List<Category> getAllCategoryAndro(@PathVariable(value="userId") int userId, @PathVariable(value="lang") int lang) {
    	 LOGGER.debug("Start categoryList.");
    	 System.out.println("GET >> getAllCategoryAndro Started.");
         List categoryListEnc = new ArrayList();
         int noOfMsgPerCat = 0;
         String noOfMsgPerCatenc = "0";
         String imgForCat = "";
         Connection conn = null;
         try
         {
           conn = DBConnection.getConnection();
             String sql = "select * from category where category_image is not null and isapproved=1";
             BASE64Encoder cryptor= new BASE64Encoder();
             Statement stmt = conn.createStatement();
             ResultSet resultSet = stmt.executeQuery(sql);
             if(lang == 1)
                 categoryListEnc.add(new Category(new String(cryptor.encode("0".getBytes())), new String(cryptor.encode("All Communications".getBytes())), "", new String(cryptor.encode("img_allCommunication_1457531396936.png".getBytes())), new String(cryptor.encode("0".getBytes())), 0));
             else
                 categoryListEnc.add(new Category(new String(cryptor.encode("0".getBytes())), new String(cryptor.encode("Semua Komunikasi".getBytes())), "", new String(cryptor.encode("img_allCommunication_1457531396936.png".getBytes())), new String(cryptor.encode("0".getBytes())), 0));
             int noOfUnreadMsg;
             String categoryName;
             String enc1;
             for(; resultSet.next(); categoryListEnc.add(new Category(enc1, categoryName, noOfMsgPerCatenc, new String(cryptor.encode(imgForCat.getBytes())), new String(cryptor.encode(String.valueOf(noOfUnreadMsg).getBytes())), noOfUnreadMsg)))
             {
                 noOfUnreadMsg = 0;
                 int categoryId = resultSet.getInt("CATEGORYID");
                 if(lang == 1)
                     categoryName = new String(cryptor.encode(String.valueOf(resultSet.getString("CATEGORYNAME")).getBytes()));
                 else
                     categoryName = new String(cryptor.encode(String.valueOf(resultSet.getString("CATEGORY_NAME_BHASA")).getBytes()));
                 enc1 = new String(cryptor.encode(String.valueOf(categoryId).getBytes()));
                 
                 String sqlPostPerCat = (new StringBuilder()).append("select count (DISTINCT md.MESSAGE_ID) from message_details md, MESSAGE_ZONE mz, MESSAGE_CHANNEL mc," +
                 		" MESSAGE m,category c where  c.CATEGORYID=").append(categoryId).append(" and m.MESSAGE_ID = md.MESSAGE_ID").append(" and md.message_id " +
                 		" IN(select message_id from message where created_at<SYSDATE and EXPIRY_AT>=SYSDATE and category_id=").append(categoryId).append("  " +
                 		" and MSG_STATUS=1 and IS_DELETED=0)").append(" and md.MESSAGE_ID=mz.MESSAGE_ID and mz.ZONE_ID IN (Select USER_ZONE.ZONE_ID from USER_ZONE " +
                 		" where USER_ZONE.USER_ID=").append(userId).append(")").append(" and md.MESSAGE_ID=mc.MESSAGE_ID and mc.CHANNEL_ID IN " +
                 		" (Select USER_CHANNEL.CHANNEL_ID from USER_CHANNEL where USER_CHANNEL.USER_ID=").append(userId).append(")").append(" and md.MESSAGE_LANG=1").toString();
                 
                 System.out.println("GET >> getAllCategoryAndro >> sqlPostPerCat : "+sqlPostPerCat);
                 LOGGER.debug("GET >> getAllCategoryAndro >> sqlPostPerCat : "+sqlPostPerCat);
                 
                 Statement stmtPostPerCat = conn.createStatement();
                 ResultSet resultSetPostPerCat = stmtPostPerCat.executeQuery(sqlPostPerCat);
                 if(resultSetPostPerCat != null && resultSetPostPerCat.next())
                 {
                     do
                     {
                         imgForCat = resultSet.getString("CATEGORY_IMAGE");
                         noOfMsgPerCat = resultSetPostPerCat.getInt(1);
                         if(categoryId != 6)
                         {
                             noOfMsgPerCatenc = new String(cryptor.encode(String.valueOf(resultSetPostPerCat.getInt(1)).getBytes()));
                             if(noOfMsgPerCat > 0)
                             {
                            	   String sqlGetFile = (new StringBuilder()).append("select  md.MESSAGE_IMG_LINK from message_details md " +
                            	   " where md.message_id IN (select max(message_id) from message where created_at<SYSDATE and EXPIRY_AT>=SYSDATE and " +
                            	   " category_id=").append(categoryId).append(" and message.MSG_STATUS=1 and message.IS_DELETED=0)and md.MESSAGE_LANG=1  and md.MESSAGE_IMG_LINK IS NOT NULL").toString();
                            	   
                                  Statement stmtGetFile = conn.createStatement();
                                 ResultSet resultSetGetFile = stmtGetFile.executeQuery(sqlGetFile);
                                 if(resultSetGetFile != null && resultSetGetFile.next())
                                     do
                                         imgForCat = resultSetGetFile.getString("MESSAGE_IMG_LINK");
                                     while(resultSetGetFile.next());
                             }
                         }
                     } while(resultSetPostPerCat.next());
                     int readMsg = 0;
                     String sqlGetreadMsg = (new StringBuilder()).append("select count(unique rr.MESSAGE_ID) from read_reciept rr, message m  where rr.USER_ID="+userId+" and rr.MESSAGE_ID=m.MESSAGE_ID and m.CATEGORY_ID="+categoryId).toString();
                     
                     Statement stmtGetreadMsg = conn.createStatement();
                     for(ResultSet resultSetGetReadMsg = stmtGetreadMsg.executeQuery(sqlGetreadMsg); resultSetGetReadMsg.next();)
                         readMsg = resultSetGetReadMsg.getInt(1);

                     if(noOfMsgPerCat > readMsg && noOfMsgPerCat != 0)
                         noOfUnreadMsg = noOfMsgPerCat - readMsg;
                 }
                 (new Category()).setCategoryId(Integer.valueOf(noOfUnreadMsg));
             }

         }
        
         catch(ClassNotFoundException e)
         {
             e.printStackTrace();
         }
         catch(Exception e)
         {
             e.printStackTrace();
         }finally{
 			DBConnection.closeConnection(conn);
 		}
         return categoryListEnc;
    }


    
    @RequestMapping(value={"/jqgrid/addcategory/{id}"}, method={RequestMethod.PUT})
    @ResponseStatus(value=HttpStatus.NO_CONTENT)
    public void updateCategory(@PathVariable(value="id") int id, @RequestBody Category category) {
        LOGGER.debug("updating category.");
        this.categoryService.updateCategory(id, category);
    }

    @RequestMapping(value={"/jqgrid/addcategory/{id}"}, method={RequestMethod.DELETE})
    @ResponseStatus(value=HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable(value="id") int id) {
        LOGGER.debug("Deleting category.");
        this.categoryService.deleteCategory(id);
    }
}