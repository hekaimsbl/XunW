package Utils;

/**
 * @Author Hekai
 * @Date 2019/4/2 10:49
 * @Description TODO
 **/
public class AlipayConfig {
    /*沙箱测试URL拼接规则*/
    /*https://openauth.alipaydev.com/oauth2/publicAppAuthorize.htm?
    app_id=APPID&
    scope=SCOPE&
    redirect_uri=ENCODED_URL*/
    /*沙箱地址*/
    //https://openapi.alipaydev.com/gateway.do
    public final static String SANDBOX_URL = "https://openapi.alipaydev.com/gateway.do";
    /**/
    public final static String URL = "https://openapi.alipay.com/gateway.do";

    public final static String APPID = "2019022563361182";

    public final static String SANDBOX_APPID = "2016092700604404";

    //应用私钥
    public final static String APP_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC+i2JT3cEcdg0Le4S//rfDLeohRLg6NbnUL1/MSIC96kmhTo+iCpC30fHsFF/vYmGnRr+5fHyS90/eRtOylPKjjLxf1YNcADg5u3tiYUVVlBUax7tav6duW1+YaGOEAhqCScE1GzV0OdMBlMo5ArT7UQIeQsfcrwJFLa8tLY4Vp2E5xUuDQivQ/OsSvYCTaeUmCdUc883LLt9xpEBOPysyyD6uoXsG3V+FwNnqgO+zXRWTd7JmOvW+itqkf3R7+6nBcnYb4w32C5aFOD8ZUXTSA71URxFTYxva6arZ/jtkA8zwmzgS9tub3Z21Y3DqWcBFi6zi2z+TYls3+LSsZD8HAgMBAAECggEBAIegPsTjOuk/PdnGeZ8Pd01+CQDVISyNwU++yLlngfusH+ypNqNoUcoFreUUO5TwugGO4jjs2t7hGgBk+ZpLWsn/CrZMC8wYe+zb/d7wzaE0XjFJqZnWgR6T+19ILk1wEd5bESLz0wB/iCm/9pDN1HRuHMvdTNVP3JXRBrdpjZDq8FELRCF3+iyLOubEYfC6nlb3yE9HH/0QzUpjoABgU8XPPIwi55d02qPLQGBtbyoipOs+/9+u4mNnvjh/6b8CYZMqQ4xh4Z1itbuA4QIKQEl1sdgdgoEGeekPh0/5yAFRiN7RNK7xpHLfJO3ra4S2TyOKEJA6g5FEkwjeEhnGzoECgYEA4iS9/sWhnjRkjzfuVG+EsNVHCHwwX1atSV0g+CcTRP2f41S9queoCtUzB62rWtwMHoWcB5ZJuMgo0DOQqXug/HwUg4ep1updbnKaBOffu6HDmXaLXjgfj4xmVqoL8UWNO9hGiRgVf9/9KCxe489lSVEC4Jovu2mmHiipy+X0yG8CgYEA17N0rac2xSsekvx8rJ33Hzuyml0nzGKW3czyCWStnFQjCBeOF+QL1KoGIyGVFvkGKLhmmp6DmaxwKzEQUp/gRXQD3XgNPCR2QCQ7HOTFbyWuUbaTNNlFmG0xiPY1J4GWmqn9j2moTzJaHb6qaO2jAMHoQ90iTDWh33jYfGD4TukCgYBSx8xc1I9i0B73Zivx8JiL+qZz7rHuVFY+gr6s1/Dv0j+TAdQkGYSj4oM5jlMpgV9WVOZWyKbX4z09aYLM55p4khsYQrLjHwtwgLbskMyPbJmjNYeyx6yV6XkUxE521VTr0TGQwaYtFxsed4+MqMCU4/i/qvFKE1RhudTd9KF0NQKBgCIySy80sVQ079XxvXpIWONUvY4HcsXOY4iqczzV/0gndp5Tza0v9Eg05GAxdMd9l2JhSbL9qnN0iRvv3VycCBw6tlooLED6rtygYdbE1iA24xoFTfU09PmjZhUGo4qt2siOZqqT36EepHbOwYl9EziNhVss9cELR6x8JiYh4XvxAoGAenq8PoalIsk5129a5fD8Mtegv67Hx1G71SWo9ZFDxnDNbluzApq67dKtE0uKPl5VxhOUIAxXc/f4gpcXu/hkDmRauy+8zcqxsuZKq/DO7+yO9d9Cyvlli6AgRkhWlT5nYUDxM8Py/16AZP0V2jW77n8oDWAo/ubl+J8PAq7POCU=";

    //参数返回格式
    public final static String FORMAT = "json";

    //编码集 可选GBK UTF-8
    public final static String CHARSET = "UTF-8";

    //支付宝公钥
    public final static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAvFJ7RQUSNM/CjvN2T5QxaGv9z73YiObIbqFFhrQ33rJfEorSRdZKrNxnw0biht4QSdThK8dqO0CoDEFk8fspJC0ei2NlxmTOjcd+3eHXBR69hx0ohI1TNq7fSHHZjA7S7rOHbWxX25ZgwD3GNphrGoskHPkTWO0uajEym1OPnw3vmS5rKV8jO10AauJ6Uvv5eH8/5INxdMtUm1YUNfSnejYwV4fKTkQMqDT8ypy78/C0uDwwjxMMFnpfH3IFPoxsHHzJ+OkzHW+VumYMxxYfBHJHj+6tGSxiYB59zZz+fv5JOcjRs822NHW9H0tu4hOKQXqmQxRQ4ZgMfIbRVKEnbQIDAQAB";

    public final static String SIGN_TYPE = "RSA2";
}
