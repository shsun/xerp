package base.tool.excel;

import java.util.Date;

/**
 * 
 * Time: 12-12-20 上午11:24
 */
public class DemoBean {
    private long id;

    private String name;

    private int age;

    private boolean sex;

    private Date birthday;

    public DemoBean() {
        super();
    }

    public DemoBean(long id, String name, int age, boolean sex, Date birthday) {
        super();
        this.id = id;
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.birthday = birthday;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean getSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

}
