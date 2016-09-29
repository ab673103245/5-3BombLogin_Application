package qianfeng.a5_3bomblogin_application;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void add(View view) { // 添加数据
        User user = new User();
        user.setUsername("zhangsan");
        user.setPassword("123");

        user.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    Toast.makeText(MainActivity.this, "添加数据成功", Toast.LENGTH_SHORT).show();
                    Log.d("google-my:", "done: 添加数据成功");
                } else {
                    Toast.makeText(MainActivity.this, "添加数据失败", Toast.LENGTH_SHORT).show();
                    Log.d("google-my:", "done: 添加数据失败");
                }
            }
        });
    }

    public void delete(View view) { // 删除数据之前也要先查询，只能通过 _id来删除数据
        // 这里都是用了new SaveListener这些回调方法，是会自己更新UI的，所以不用你自己写handler来更新UI
        // 可以直接在这些回调方法内更新UI
        BmobQuery<User> query = new BmobQuery<>();

        query.addWhereEqualTo("username", "zhangsan"); // 要查询的数据是"zhangsan"，要想删除，Bmob的数据库要求必须用idObejct，这就必须要先查询

        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                // 这里的 e == null ,只是表明网络没有问题，并没有表明一定有你查询的数据,要看查询成功不成功，要用list集合来判断
                if (e == null) // 网络连接没有问题
                {
                    Log.d("google-my:", "done: 网络连接没有问题");
                    if (list != null && list.size() > 0) // 证明查询到数据
                    {
                        Log.d("google-my:", "done: 查询到数据了");
                        for (User use : list) {
                            // use.delete(): 从网络数据库中删除数据，一定要根据数据条的_id来删除
                            use.delete(new UpdateListener() { // 回调方法中，可以更新UI，查询肯定是在子线程中进行的
                                @Override
                                public void done(BmobException e) {
                                    if (e == null) {
                                        Log.d("google-my:", "done: 删除成功");
                                    }
                                }
                            });
                        }
                    }
                } else {
                    Log.d("google-my:", "done: 网络连接失败");
                }

            }
        });

    }

    public void search(View view) {  // 查看数据
        BmobQuery<User> bmobQuery = new BmobQuery<>();

        bmobQuery.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if (e == null) {
                    Log.d("google-my:", "done: 连接成功");
                    if (list != null && list.size() > 0) { // 确保查询到结果
                        String createdAt = list.get(0).getCreatedAt();
                        Log.d("google-my:", "done: username:" + list.get(0).getUsername() + ";创建日期:"+createdAt);
                        Toast.makeText(MainActivity.this,createdAt,Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.d("google-my:", "done: 网络连接失败");
                }

            }
        });

    }
}
