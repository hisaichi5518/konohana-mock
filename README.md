# Konohana

```java
@Store
interface User {

    @Key
    int id = -1;

    @Key
    String name = "default-name";
}
```


```java
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Konohana konohana = new Konohana(this);

        int id = konohana.storeOfUser().getId(); // Return -1
        konohana.storeOfUser().changes().subscribe(...);
        konohana.storeOfUser().idChanges().subscribe(...);
    }
}
```

