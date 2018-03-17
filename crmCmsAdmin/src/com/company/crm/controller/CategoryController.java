package com.company.crm.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import net.coobird.thumbnailator.Thumbnails;

import org.apache.commons.io.FilenameUtils;
import org.cryptonode.jncryptor.AES256JNCryptor;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriTemplate;

import sun.misc.BASE64Encoder;

import com.company.crm.constant.URIConstants;
import com.company.crm.model.Category;
import com.company.crm.model.ConstantDb;
import com.company.crm.model.User;
import com.company.crm.service.CategoryService;
import com.company.crm.util.DBConnection;
import com.company.crm.util.Page;

@Controller
@SuppressWarnings({"rawtypes","unused","unchecked"})
public class CategoryController {
    ConstantDb constantDb = ConstantDb.getConstantDb();
    private static final Logger LOGGER = LoggerFactory.getLogger((Class)CategoryController.class);
    
    private static final String UploadFolder = "/webapps/communicator/android/";
	private static final String UploadFolderAndroid = "/webapps/communicator/android/";
	private static final String EditFolder = "/webapps/communicator/EditImage/";
	private static final String cerFolder="/webapps/communicator/Apple/";
    
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
        User user = (User)request.getSession().getAttribute("user");
        this.categoryService.addCategory(category);
        URI uri = new UriTemplate("{requestUrl}/{id}").expand(new Object[]{request.getRequestURL().toString(), 2004});
        HttpHeaders headers = new HttpHeaders();
        headers.put("Location", Collections.singletonList(uri.toASCIIString()));
        return new ResponseEntity((MultiValueMap)headers, HttpStatus.CREATED);
    }

    @RequestMapping(value={"/addcategory"}, method={RequestMethod.POST})
    public String addCategory(@Valid @ModelAttribute Category category, BindingResult result, Model model, HttpServletRequest request) {
        if (!result.hasErrors()) {
            User user = (User)request.getSession().getAttribute("user");
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

	@RequestMapping(value={"/restcategorylist"}, method={RequestMethod.GET})
    @ResponseBody
    public List<Category> getAllCategory() {
        LOGGER.debug("Start categoryList.");
        List categoryList = this.categoryService.getCategoryList();
        return categoryList;
    }

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
            
           	conn = DBConnection.getConnection();

            String sql = "select * from category where category_image is not null";
           // AES256JNCryptor cryptor = new AES256JNCryptor();
            BASE64Encoder base64encoder = new BASE64Encoder();
           // String password = "aes123";
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
                        if (noOfMsgPerCat <= 0) continue;
                        String sqlGetFile = "select  md.MESSAGE_IMG_LINK from message_details md ,MESSAGE_ZONE mz, MESSAGE_CHANNEL mc where md.message_id IN(select max(message_id) from message where created_at<SYSDATE and EXPIRY_AT>=SYSDATE and category_id=" + categoryId + " and md.MESSAGE_ID=mz.MESSAGE_ID and mz.ZONE_ID IN (Select USER_ZONE.ZONE_ID from USER_ZONE where USER_ZONE.USER_ID=" + userId + ")" + " and md.MESSAGE_ID=mc.MESSAGE_ID and mc.CHANNEL_ID IN (Select USER_CHANNEL.CHANNEL_ID from USER_CHANNEL where USER_CHANNEL.USER_ID=" + userId + ")" + " and message.MSG_STATUS=1 and message.IS_DELETED=0)and md.MESSAGE_LANG=1  and md.MESSAGE_IMG_LINK IS NOT NULL";
                        Statement stmtGetFile = conn.createStatement();
                        ResultSet resultSetGetFile = stmtGetFile.executeQuery(sqlGetFile);
                        if (resultSetGetFile == null || !resultSetGetFile.next()) continue;
                        do {
                            imgForCat = resultSetGetFile.getString("MESSAGE_IMG_LINK");
                        } while (resultSetGetFile.next());
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
        ArrayList<Category> categoryListEnc = new ArrayList<Category>();
        int noOfMsgPerCat = 0;
        String noOfMsgPerCatenc = "0";
        String imgForCat = "";
        Connection conn = null;
        try {
        	conn = DBConnection.getConnection();
        	
            String sql = "select * from category where category_image is not null";
            //String sql = "select * from category";
            AES256JNCryptor cryptor = new AES256JNCryptor();
            BASE64Encoder base64encoder = new BASE64Encoder();
            String password = "aes123";
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
                String sqlPostPerCat = "select count (DISTINCT md.MESSAGE_ID) from message_details md, MESSAGE_ZONE mz, MESSAGE_CHANNEL mc,MESSAGE m,category c where  c.CATEGORYID=" + categoryId + " and m.MESSAGE_ID = md.MESSAGE_ID" + " and md.message_id IN(select message_id from message where created_at<SYSDATE and EXPIRY_AT>=SYSDATE and category_id=" + categoryId + " and MSG_STATUS=1 and IS_DELETED=0)" + " and md.MESSAGE_ID=mz.MESSAGE_ID and mz.ZONE_ID IN (Select USER_ZONE.ZONE_ID from USER_ZONE where USER_ZONE.USER_ID=" + userId + ")" + " and md.MESSAGE_ID=mc.MESSAGE_ID and mc.CHANNEL_ID IN (Select USER_CHANNEL.CHANNEL_ID from USER_CHANNEL where USER_CHANNEL.USER_ID=" + userId + ")" + " and md.MESSAGE_LANG=1";
                Statement stmtPostPerCat = conn.createStatement();
                ResultSet resultSetPostPerCat = stmtPostPerCat.executeQuery(sqlPostPerCat);
                if (resultSetPostPerCat != null && resultSetPostPerCat.next()) {
                    do {
                        imgForCat = resultSet.getString("CATEGORY_IMAGE");
                        noOfMsgPerCat = resultSetPostPerCat.getInt(1);
                        if (categoryId == 6) continue;
                        String categaryidcheck=base64encoder.encode(String.valueOf(resultSetPostPerCat.getInt(1)).getBytes());
                        noOfMsgPerCatenc = new String(categaryidcheck);
                        if (noOfMsgPerCat <= 0) continue;
                        String sqlGetFile="";
                        if(lang==0){
                         sqlGetFile = "select  md.MESSAGE_IMG_LINK from message_details md ,MESSAGE_ZONE mz, MESSAGE_CHANNEL mc where md.message_id IN(select max(message_id) from message where created_at<SYSDATE and EXPIRY_AT>=SYSDATE and category_id=" + categoryId + " and md.MESSAGE_ID=mz.MESSAGE_ID and mz.ZONE_ID IN (Select USER_ZONE.ZONE_ID from USER_ZONE where USER_ZONE.USER_ID=" + userId + ")" + " and md.MESSAGE_ID=mc.MESSAGE_ID and mc.CHANNEL_ID IN (Select USER_CHANNEL.CHANNEL_ID from USER_CHANNEL where USER_CHANNEL.USER_ID=" + userId + ")" + " and message.MSG_STATUS=1 and message.IS_DELETED=0)and md.MESSAGE_LANG=0  and md.MESSAGE_IMG_LINK IS NOT NULL";
                         
                        }
                        else{
                        	/* sqlGetFile = "select  md.MESSAGE_IMG_LINK from message_details md ,MESSAGE_ZONE mz, MESSAGE_CHANNEL mc where md.message_id 
                        	IN(select max(message_id) from message where created_at<SYSDATE and EXPIRY_AT>=SYSDATE and category_id=" " +
                        	"+ categoryId + " and md.MESSAGE_ID=mz.MESSAGE_ID and mz.ZONE_ID IN (Select USER_ZONE.ZONE_ID from USER_ZONE 
                        			where USER_ZONE.USER_ID=" + userId + ")" + " and md.MESSAGE_ID=mc.MESSAGE_ID and mc.CHANNEL_ID IN 
                        			(Select USER_CHANNEL.CHANNEL_ID from USER_CHANNEL where USER_CHANNEL.USER_ID=" + userId + ")" + " 
                        		and message.MSG_STATUS=1 and message.IS_DELETED=0)and md.MESSAGE_LANG=1  and md.MESSAGE_IMG_LINK IS NOT NULL";
                        	*/
                        	//chg by Bilal  
                        	sqlGetFile="select md.MESSAGE_IMG_LINK from message_details md ,MESSAGE_ZONE mz, MESSAGE_CHANNEL mc where md.message_id IN (select max(message_id) from message where created_at<SYSDATE and EXPIRY_AT>=SYSDATE and category_id='3' and message.MSG_STATUS=1 and message.IS_DELETED=0)and md.MESSAGE_LANG=1  and md.MESSAGE_IMG_LINK IS NOT NULL";
                        }
                        Statement stmtGetFile = conn.createStatement();
                        ResultSet resultSetGetFile = stmtGetFile.executeQuery(sqlGetFile);
                        if (resultSetGetFile == null || !resultSetGetFile.next()) continue;
                        do {
                            imgForCat = resultSetGetFile.getString("MESSAGE_IMG_LINK");
                        } while (resultSetGetFile.next());
                    } while (resultSetPostPerCat.next());
                    int readMsg = 0;
                    String sqlGetreadMsg =""; 
					
					if(lang==1){
                 sqlGetreadMsg = "select count (DISTINCT md.MESSAGE_ID) from message_details md, MESSAGE_ZONE mz, MESSAGE_CHANNEL mc,MESSAGE m,category c where  c.CATEGORYID=" + categoryId + " and m.MESSAGE_ID = md.MESSAGE_ID" + " and md.message_id IN(select message_id from message where created_at<SYSDATE and EXPIRY_AT>=SYSDATE and category_id=" + categoryId + " and MSG_STATUS=1 and IS_DELETED=0)" + " and md.MESSAGE_ID=mz.MESSAGE_ID and mz.ZONE_ID IN (Select USER_ZONE.ZONE_ID from USER_ZONE where USER_ZONE.USER_ID=" + userId + ")" + " and md.MESSAGE_ID=mc.MESSAGE_ID and mc.CHANNEL_ID IN (Select USER_CHANNEL.CHANNEL_ID from USER_CHANNEL where USER_CHANNEL.USER_ID=" + userId + ")" + " and md.MESSAGE_LANG=1";
                }else
                	sqlGetreadMsg = "select count (DISTINCT md.MESSAGE_ID) from message_details md, MESSAGE_ZONE mz, MESSAGE_CHANNEL mc,MESSAGE m,category c where  c.CATEGORYID=" + categoryId + " and m.MESSAGE_ID = md.MESSAGE_ID" + " and md.message_id IN(select message_id from message where created_at<SYSDATE and EXPIRY_AT>=SYSDATE and category_id=" + categoryId + " and MSG_STATUS=1 and IS_DELETED=0)" + " and md.MESSAGE_ID=mz.MESSAGE_ID and mz.ZONE_ID IN (Select USER_ZONE.ZONE_ID from USER_ZONE where USER_ZONE.USER_ID=" + userId + ")" + " and md.MESSAGE_ID=mc.MESSAGE_ID and mc.CHANNEL_ID IN (Select USER_CHANNEL.CHANNEL_ID from USER_CHANNEL where USER_CHANNEL.USER_ID=" + userId + ")" + " and md.MESSAGE_LANG=0";
                
				
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
        catch (Exception e) {
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
    
    
    public String createFile(MultipartFile file) {
		String fileName = "";
		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();
				fileName = FilenameUtils.getName(file.getOriginalFilename());
				String type = fileName.substring(fileName.lastIndexOf("."));
				String name = fileName.substring(0, fileName.lastIndexOf("."));
				fileName = name.trim() + "_" + new Date().getTime() + type;
				String base = System.getProperty("catalina.base");

				File dir = new File(base + File.separator + UploadFolder);
//				For Android
				File dirAndroid = new File(base + File.separator + UploadFolderAndroid);

				if (!dir.exists()) {
					dir.mkdirs();
				}
//				For ANdroid
				if (!dirAndroid.exists()) {
					dirAndroid.mkdirs();
				}
				File dateDir = new File(base + File.separator + UploadFolder + File.separator + File.separator);
//				For Android
				File dateDirAndroid = new File(base + File.separator + UploadFolderAndroid + File.separator + File.separator);

				if (!dateDir.exists()) {
					dateDir.mkdirs();
				}
//For ANdroid
				if (!dateDirAndroid.exists()) {
					dateDirAndroid.mkdirs();
				}


				File uploadedFile = new File(dateDir.getAbsolutePath() + File.separator + fileName);
				
//				For Android
				File uploadedFileAndroid = new File(dateDirAndroid.getAbsolutePath() + File.separator + fileName);

				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(uploadedFile));
//				For Android
				BufferedOutputStream streamAndroid = new BufferedOutputStream(new FileOutputStream(uploadedFileAndroid));
				
				

				stream.write(bytes);
				streamAndroid.write(bytes);
				stream.close();
				streamAndroid.close();

				if(type.equalsIgnoreCase(".jpg") || type.equalsIgnoreCase(".png") || type.equalsIgnoreCase(".jpeg"))
				{
					//.size(600, 600)
					Thumbnails.of(uploadedFile).size(600, 600).toFile(uploadedFile);
					//.size(600, 600)
					Thumbnails.of(uploadedFileAndroid).size(600, 600).toFile(uploadedFileAndroid);
				}
				//Commented by sudd
				//writeEncryptedFile(uploadedFile, fileName);
				

			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
		return fileName;
	}

	
	
	@RequestMapping(value = URIConstants.MY_CATEGORY, method = RequestMethod.GET)
	public String myCategory(HttpServletResponse response, Model model, HttpSession session) {
		LOGGER.debug("Start categoryList.");
		List<Category> categoryList = new ArrayList<Category>();

		List<String> array = new ArrayList<String>();
		array.add("array1");
		Connection conn = null;

		try {
			conn = DBConnection.getConnection();
			User user1 = (User) session.getAttribute("user");
			 String strUserName=user1.getUserName();
			 String sql="";
			 if(strUserName.equalsIgnoreCase("admin")|| strUserName.equalsIgnoreCase("banca"))
			 {
			  sql=" select a.categoryid,a.categoryname,a.category_name_bhasa,a.category_image,a.isapproved from category a where nvl(a.isapproved,0) = 1 and a.username='"+strUserName+"' order by a.categoryid desc";
			 }
			 else{
				 sql=" select a.categoryid,a.categoryname,a.category_name_bhasa,a.category_image,a.isapproved from category a where nvl(a.isapproved,0) = 1 order by a.categoryid desc";	 
			 }
			Statement stmt = conn.createStatement();
			ResultSet resultSet = stmt.executeQuery(sql);

			while (resultSet.next()) {
				int categoryId = resultSet.getInt("categoryid");
				String categoryName = String.valueOf(resultSet.getString("categoryname"));
				String category_name_bhasa = String.valueOf(resultSet.getString("category_name_bhasa"));
				String category_image = String.valueOf(resultSet.getString("category_image"));
				String isapproved = String.valueOf(resultSet.getInt("isapproved")).equalsIgnoreCase("0") ? "Pending" : "Approved";
				
				categoryList.add(new Category(categoryId, categoryName, isapproved,category_name_bhasa,category_image));
	
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnection(conn);
		}

		model.addAttribute("categoryList", categoryList);
		
		return "mycategory";

	}
	
	@RequestMapping(value = URIConstants.SAVE_CATEGORY , method = RequestMethod.POST)
	public String savecategory(HttpServletResponse response,Model model, HttpServletRequest request,@RequestParam("file_image") MultipartFile file_image)throws IOException {
		List<Category> categoryList = new ArrayList<Category>();
		Connection conn = null;
		try {
			conn = DBConnection.getConnection();
			String strcategoryid = request.getParameter("categoryId");
			String strcategoryName = request.getParameter("categoryName");
			String strcategoryNameBahasa = request.getParameter("category_name_bhasa");
            String strUserName=request.getParameter("userName");
			//Start image code
			
			String img_file1 = "";
			if(!file_image.isEmpty())
			{
				img_file1 = createFile(file_image);
				
			}
			PrintWriter script = response.getWriter();
			
			
			/*if (!file.isEmpty()) {
				try {
					// model.addAttribute("messageName", "in");
					byte[] bytes = file.getBytes();

					fileName = FilenameUtils.getName(file.getOriginalFilename());
					String type = fileName.substring(fileName.lastIndexOf("."));
					String name = fileName.substring(0, fileName.lastIndexOf("."));
					// String

					f1 = name.trim() + "_" + new Date().getTime() + type;
					fandroid = name.trim() + "_" + new Date().getTime() + type;
					String base = System.getProperty("catalina.base");

					File dir = new File(base + File.separator + UploadFolder);
					Test For Android
					File dirAndroid = new File(base + File.separator + UploadFolderAndroid);
					
					if (!dir.exists()) {
						dir.mkdirs();
					}
//					For Android
					if (!dirAndroid.exists()) {
						dirAndroid.mkdirs();
					}

					File dateDir = new File(base + File.separator + UploadFolder + File.separator + File.separator);
					//For ANdroid
					File dateDirAndroid = new File(base + File.separator + UploadFolderAndroid + File.separator + File.separator);
					if (!dateDir.exists()) {
						dateDir.mkdirs();
					}
//					For Android
					if (!dateDirAndroid.exists()) {
						dateDirAndroid.mkdirs();
					}
					
					File uploadedFile = new File(dateDir.getAbsolutePath() + File.separator + f1);
					
				
//					For Android
					File uploadedFileAndroid = new File(dateDirAndroid.getAbsolutePath() + File.separator + f1);
					

					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(uploadedFile));
					BufferedOutputStream streamAndroid = new BufferedOutputStream(new FileOutputStream(uploadedFileAndroid));
					

				      Iterator<ImageWriter>writers =  ImageIO.getImageWritersByFormatName("jpg");
				      ImageWriter writer = (ImageWriter) writers.next();
					ImageOutputStream ios = ImageIO.createImageOutputStream(os);
				      writer.setOutput(ios);

				      ImageWriteParam param = writer.getDefaultWriteParam();
				      
				      param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
				      param.setCompressionQuality(0.05f);

					
					stream.write(bytes);
					streamAndroid.write(bytes);
					stream.close();
					streamAndroid.close();

					model.addAttribute("typeFile", "type="+type);
					if(type.equalsIgnoreCase(".jpg") || type.equalsIgnoreCase(".png") || type.equalsIgnoreCase(".jpeg"))
					{
						//File resizedFile=new File( base + File.separator + UploadFolder + fileName);
						//.size(600, 600)
						Thumbnails.of(uploadedFile).size(600, 600).toFile(uploadedFile);
						//.size(600, 600)
						Thumbnails.of(uploadedFileAndroid).size(600, 600).toFile(uploadedFileAndroid);
					}

					writeEncryptedFile(uploadedFile, f1);
					//writeEncryptedFileForAndroid(uploadedFile, f1);
					
				} catch (Exception e) {
					//script.write("ok1212");
					// model.addAttribute("msg", "Exception");

				}
			} 
			else {

			}*/
			
			// End image code
			
			
			int rowcount = 0;
			String sql4 = "select count(*) as rowcount from category a where a.categoryname = '"+strcategoryName+"'";
			Statement stmt4 = conn.createStatement();
			ResultSet resultSet4 = stmt4.executeQuery(sql4);
			System.out.println("sql111111========" + sql4);
	
			while (resultSet4.next()) {
				rowcount = resultSet4.getInt("rowcount");
			}
			
			if(rowcount == 0){
				String sql = "insert into category  (categoryid,categoryname,category_name_bhasa,category_image,isapproved,action,username) "
						+ "values(CATEGORY_SEQ.nextval,?,?,?,0,0,?)";
				PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				stmt.setString(1, strcategoryName);
				stmt.setString(2, strcategoryNameBahasa);
				stmt.setString(3, img_file1);
				stmt.setString(4, strUserName);
				System.out.println("sql22222====" + sql);
				int queryresult = stmt.executeUpdate();	
			}		
			
			String sql=" select a.categoryid,a.categoryname,a.category_name_bhasa,a.category_image,a.isapproved from category a where nvl(a.isapproved,0) = 1 and a.username='"+strUserName+"' order by a.categoryid desc";
			Statement stmt = conn.createStatement();
			ResultSet resultSet = stmt.executeQuery(sql);
			System.out.println("sql33333332======" + sql);

			while (resultSet.next()) {
				int categoryId = resultSet.getInt("categoryid");
				String categoryName = String.valueOf(resultSet.getString("categoryname"));
				String category_name_bhasa = String.valueOf(resultSet.getString("category_name_bhasa"));
				String category_image = String.valueOf(resultSet.getString("category_image"));
				String isapproved = String.valueOf(resultSet.getInt("isapproved")).equalsIgnoreCase("0") ? "Pending" : "Approved";
				
				categoryList.add(new Category(categoryId, categoryName, isapproved,category_name_bhasa,category_image));
	
			}
			model.addAttribute("categoryList", categoryList);
			model.addAttribute("iscategoryAdded", (Object) true);
			if(rowcount == 0){
				model.addAttribute("iscategoryAdded", (Object) true);
			}else{
				model.addAttribute("isDuplicate", (Object) true);
			}
			conn.close();
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		} catch (Exception e) {

			e.printStackTrace();
		}finally{
			DBConnection.closeConnection(conn);
		} 

		return "mycategory";
	}
	
	@RequestMapping(value = "/updatecategory/{id}/{name}/{category_name_bhasa}" , method = RequestMethod.GET)
	public String updatecategory(@PathVariable("id") int id,@PathVariable("name") String name,@PathVariable("category_name_bhasa") String category_name_bhasa1,HttpServletResponse response,Model model, HttpServletRequest request)throws IOException {
		List<Category> categoryList = new ArrayList<Category>();
		Connection conn = null;
		try {
			conn = DBConnection.getConnection();
			int rowcount = 0;
			String sql4 = "select count(*) as rowcount from category a where a.categoryname = '"+name+"' and a.categoryid !="+ id;
			Statement stmt4 = conn.createStatement();
			ResultSet resultSet4 = stmt4.executeQuery(sql4);
			System.out.println("sql5555555" + sql4);
			
			while (resultSet4.next()) {
				rowcount = resultSet4.getInt("rowcount");
			}
			if(rowcount == 0){
				String sql="UPDATE category SET categoryname = '"+name+"',category_name_bhasa = '"+category_name_bhasa1+"',isapproved = '0',action = '1' WHERE categoryid="+ id;
				Statement stmt = conn.createStatement();
				stmt.executeUpdate(sql);
			}
			
			String sql=" select a.categoryid,a.categoryname,a.category_name_bhasa,a.category_image,a.isapproved from category a where nvl(a.isapproved,0) = 1 order by a.categoryid desc";
			Statement stmt = conn.createStatement();
			ResultSet resultSet = stmt.executeQuery(sql);

			while (resultSet.next()) {
				int categoryId = resultSet.getInt("categoryid");
				String categoryName = String.valueOf(resultSet.getString("categoryname"));
				String category_name_bhasa = String.valueOf(resultSet.getString("category_name_bhasa"));
				String category_image = String.valueOf(resultSet.getString("category_image"));
				String isapproved = String.valueOf(resultSet.getInt("isapproved")).equalsIgnoreCase("0") ? "Pending" : "Approved";
				
				categoryList.add(new Category(categoryId, categoryName, isapproved,category_name_bhasa,category_image));
	
			}
			model.addAttribute("categoryList", categoryList);
			if(rowcount == 0){
				model.addAttribute("iscategoryAdded", (Object) true);
			}else{
				model.addAttribute("isDuplicate", (Object) true);
			}
			conn.close();
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		} catch (Exception e) {

			e.printStackTrace();
		} finally{
			DBConnection.closeConnection(conn);
		}

		return "mycategory";
	}
	
	@RequestMapping(value = "/viewcategory/{id}", method = RequestMethod.GET)
	public String viewcategory(@PathVariable("id") int id, Model model, HttpSession session) throws SQLException {
		List<Category> categoryList = new ArrayList<Category>();
		Connection conn = null;
		try{
			conn = DBConnection.getConnection();
			 User user1 = (User) session.getAttribute("user");
			 String strUserName=user1.getUserName();
			 String sql="";
			 if(strUserName.equalsIgnoreCase("admin")|| strUserName.equalsIgnoreCase("banca"))
			 {
				 sql=" select a.categoryid,a.categoryname,a.category_name_bhasa,a.category_image,a.isapproved from category a where nvl(a.isapproved,0) = 1 and a.username='"+strUserName+"' order by a.categoryid desc";
			 }
			 else{
				 sql=" select a.categoryid,a.categoryname,a.category_name_bhasa,a.category_image,a.isapproved from category a where nvl(a.isapproved,0) = 1  order by a.categoryid desc";	 
			 }
			//String sql=" select a.categoryid,a.categoryname,a.category_name_bhasa,a.category_image,a.isapproved from category a where nvl(a.isapproved,0) = 1 and a.username='"+strUserName+"' order by a.categoryid";
			Statement stmt = conn.createStatement();
			ResultSet resultSet = stmt.executeQuery(sql);

			while (resultSet.next()) {
				int categoryId = resultSet.getInt("categoryid");
				String categoryName = String.valueOf(resultSet.getString("categoryname"));
				String category_name_bhasa = String.valueOf(resultSet.getString("category_name_bhasa"));
				String category_image = String.valueOf(resultSet.getString("category_image"));
				String isapproved = String.valueOf(resultSet.getInt("isapproved")).equalsIgnoreCase("0") ? "Pending" : "Approved";
				
				categoryList.add(new Category(categoryId, categoryName, isapproved,category_name_bhasa,category_image));
	
			}
			
			String sql1=" select a.categoryid,a.categoryname,a.category_name_bhasa,a.category_image,a.isapproved from category a where a.categoryid = "+ id +" and nvl(a.isapproved,0) = 1";
			Statement stmt1 = conn.createStatement();
			ResultSet resultSet1 = stmt1.executeQuery(sql1);
			String strCategoryName = "";
			String strCategory_name_bhasa = "";
			String strCategory_image = "";
			int  strCategoryId = 0;
			while (resultSet1.next()) {
				strCategoryId = resultSet1.getInt("categoryid");
				strCategoryName = resultSet1.getString("categoryname");
				strCategory_name_bhasa = resultSet1.getString("category_name_bhasa");
				strCategory_image = resultSet1.getString("category_image");
				
			} 
			
			model.addAttribute("categoryList", categoryList);
			model.addAttribute("displayMsg", (Object) true);
			model.addAttribute("isView", (Object) true);
			model.addAttribute("strCategoryId", strCategoryId);
			model.addAttribute("strCategoryName", strCategoryName);
			model.addAttribute("strCategory_name_bhasa", strCategory_name_bhasa);
			model.addAttribute("strCategory_image", strCategory_image);
		}catch(Exception e){
			e.printStackTrace();
		}

		return "mycategory";

	}
	@RequestMapping(value = "/editcategory/{id}", method = RequestMethod.GET)
	public String editcategory(@PathVariable("id") int id, Model model, HttpSession session) throws SQLException {
		List<Category> categoryList = new ArrayList<Category>();
		Connection conn = null;
		try{
			conn = DBConnection.getConnection();
			 User user1 = (User) session.getAttribute("user");
			 String strUserName=user1.getUserName();
			 String sql="";
			 if(strUserName.equalsIgnoreCase("admin")|| strUserName.equalsIgnoreCase("banca"))
			 {
				 sql=" select a.categoryid,a.categoryname,a.category_name_bhasa,a.category_image,a.isapproved from category a where nvl(a.isapproved,0) = 1 and a.username='"+strUserName+"' order by a.categoryid desc";
			 }
			 else{
				 sql=" select a.categoryid,a.categoryname,a.category_name_bhasa,a.category_image,a.isapproved from category a where nvl(a.isapproved,0) = 1  order by a.categoryid desc";	 
			 }
			//String sql=" select a.categoryid,a.categoryname,a.category_name_bhasa,a.category_image,a.isapproved from category a where nvl(a.isapproved,0) = 1 and a.username='"+strUserName+"' order by a.categoryid";
			Statement stmt = conn.createStatement();
			ResultSet resultSet = stmt.executeQuery(sql);

			while (resultSet.next()) {
				int categoryId = resultSet.getInt("categoryid");
				String categoryName = String.valueOf(resultSet.getString("categoryname"));
				String category_name_bhasa = String.valueOf(resultSet.getString("category_name_bhasa"));
				String category_image = String.valueOf(resultSet.getString("category_image"));
				String isapproved = String.valueOf(resultSet.getInt("isapproved")).equalsIgnoreCase("0") ? "Pending" : "Approved";
				
				categoryList.add(new Category(categoryId, categoryName, isapproved,category_name_bhasa,category_image));
	
			}
			
			String sql1=" select a.categoryid,a.categoryname,a.category_name_bhasa,a.category_image,a.isapproved from category a where a.categoryid = "+ id +" and nvl(a.isapproved,0) = 1";
			Statement stmt1 = conn.createStatement();
			ResultSet resultSet1 = stmt1.executeQuery(sql1);
			String strCategoryName = "";
			String strCategory_name_bhasa = "";
			String strCategory_image = "";
			int  strCategoryId = 0;
			while (resultSet1.next()) {
				strCategoryId = resultSet1.getInt("categoryid");
				strCategoryName = resultSet1.getString("categoryname");
				strCategory_name_bhasa = resultSet1.getString("category_name_bhasa");
				strCategory_image = resultSet1.getString("category_image");
			} 
			
			model.addAttribute("categoryList", categoryList);
			model.addAttribute("displayMsg", (Object) true);
			model.addAttribute("isView", (Object) false);
			model.addAttribute("strCategoryId", strCategoryId);
			model.addAttribute("strCategoryName", strCategoryName);
			model.addAttribute("strCategory_name_bhasa", strCategory_name_bhasa);
			model.addAttribute("strCategory_image", strCategory_image);
		}catch(Exception e){
			e.printStackTrace();
		}

		return "mycategory";

	}
	@RequestMapping(value = "/deletecategory/{id}", method = RequestMethod.GET)
	public String deletecategory(@PathVariable("id") int id, Model model, HttpSession session) throws SQLException {
		List<Category> categoryList = new ArrayList<Category>();
		Connection conn = null;
		try{
			conn = DBConnection.getConnection();
			 User user1 = (User) session.getAttribute("user");
			 String strUserName=user1.getUserName();
			/*String sql="delete from category where categoryid="+ id;
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(sql);*/
			
			String sql="UPDATE CATEGORY SET ISAPPROVED ='0',ACTION='2'  WHERE categoryid="+ id;
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			String sql1="";
			 if(strUserName.equalsIgnoreCase("admin")|| strUserName.equalsIgnoreCase("banca"))
			 {
				 sql1=" select a.categoryid,a.categoryname,a.category_name_bhasa,a.category_image,a.isapproved from category a where nvl(a.isapproved,0) = 1 and a.username='"+strUserName+"' order by a.categoryid desc";
			 }
			 else{
				 sql1=" select a.categoryid,a.categoryname,a.category_name_bhasa,a.category_image,a.isapproved from category a where nvl(a.isapproved,0) = 1  order by a.categoryid desc";	 
			 }
			//String sql1=" select a.categoryid,a.categoryname,a.category_name_bhasa,a.category_image,a.isapproved from category a where nvl(a.isapproved,0) = 1 and a.username='"+strUserName+"' order by a.categoryid";
			Statement stmt1 = conn.createStatement();
			ResultSet resultSet = stmt1.executeQuery(sql1);

			while (resultSet.next()) {
				int categoryId = resultSet.getInt("categoryid");
				String categoryName = String.valueOf(resultSet.getString("categoryname"));
				String category_name_bhasa = String.valueOf(resultSet.getString("category_name_bhasa"));
				String category_image = String.valueOf(resultSet.getString("category_image"));
				String isapproved = String.valueOf(resultSet.getInt("isapproved")).equalsIgnoreCase("0") ? "Pending" : "Approved";
				
				categoryList.add(new Category(categoryId, categoryName, isapproved,category_name_bhasa,category_image));
	
			}
			
			model.addAttribute("categoryList", categoryList);
			model.addAttribute("displayMsg", (Object) true);
			
		}catch(Exception e){
			e.printStackTrace();
		}

		return "mycategory";

	}
	
	
	@RequestMapping(value="/pendingcategory", method = RequestMethod.GET)
	public String pendingcategory(Model model,HttpServletResponse response, HttpSession session,HttpServletRequest request) throws ClassNotFoundException, SQLException {
		LOGGER.debug("Start messageList.");
		List<Category> categoryList = new ArrayList<Category>();
		Connection conn = null;
		try{
			conn = DBConnection.getConnection();
			 User user1 = (User) session.getAttribute("user");
			 String strUserName=user1.getUserName();

			 String sql="";
			 if(strUserName.equalsIgnoreCase("admin")|| strUserName.equalsIgnoreCase("banca"))
			 {
				 sql=" select a.categoryid,a.categoryname,a.category_name_bhasa,a.category_image,a.isapproved,a.action from category a where nvl(a.isapproved,0) = 0 and a.username='"+strUserName+"' order by a.categoryid desc";
			 }
			 else{
				 sql=" select a.categoryid,a.categoryname,a.category_name_bhasa,a.category_image,a.isapproved,a.action from category a where nvl(a.isapproved,0) = 0  order by a.categoryid desc";	 
			 }
			
			Statement stmt = conn.createStatement();
			ResultSet resultSet = stmt.executeQuery(sql);
			System.out.println("pendingcategory >> SQL >>" + sql);
			while (resultSet.next()) {
				
				int categoryId = resultSet.getInt("categoryid");
				int action = resultSet.getInt("action");
				String categoryName = String.valueOf(resultSet.getString("categoryname"));
				String category_name_bhasa = String.valueOf(resultSet.getString("category_name_bhasa"));
				String category_image = String.valueOf(resultSet.getString("category_image"));
				String isapproved = String.valueOf(resultSet.getInt("isapproved")).equalsIgnoreCase("0") ? "Pending" : "Approved";
			
				System.out.println("category id="+categoryId+" category name "+categoryName);
				
				categoryList.add(new Category(categoryId, categoryName, isapproved,category_name_bhasa,category_image,action));
	
			}
			
			model.addAttribute("categoryList", categoryList);
			
			model.addAttribute("category", new Category());
		}catch(Exception e){
			e.printStackTrace();
		}
	
		return "pendingCategory";

	}
	
	@RequestMapping(value = "/approvecategory", method = RequestMethod.GET)
	public  String approveCategory(Model model,HttpServletResponse response,@RequestParam("catid") String  catid) 
	{
		Connection conn = null;
		
		try {
			conn = DBConnection.getConnection();
			String sql = " select a.action from category a WHERE categoryid="
					+ catid;
			
			Statement stmt = conn.createStatement();
			ResultSet resultSet = stmt.executeQuery(sql);
			System.out.println("sql777777" + sql);
			int action = 0;
			while (resultSet.next()) {
				action = resultSet.getInt("action");
				System.out.println("action " + action);

			}

			if (action == 2) {
				System.out.println("inside if condition");
				String sql1 = "delete from category where categoryid=" + catid;
				Statement stmt1 = conn.createStatement();
				stmt1.executeUpdate(sql1);

			} else {

				System.out.println("inside else condition");
				String sql2 = "UPDATE category SET ISAPPROVED ='1' WHERE categoryid="
						+ catid;
				Statement stmt2 = conn.createStatement();
				stmt2.executeUpdate(sql2);
			}

		}catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (Exception e) {
			
			e.printStackTrace();
		}finally{
			DBConnection.closeConnection(conn);
		}
		return "categoryapproved";
		
	}
	
    
    
    
    
    
}