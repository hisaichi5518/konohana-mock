# Konohana

```java
@Store
public abstract class User {

    @Key
    int id;

    @Key
    String name;
}
```


```java
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Konohana konohana = new Konohana(this);

        int id = konohana.storeOfUser().getId();
        konohana.storeOfUser().changes().subscribe(...);
    }
}
```

