package CollectionOfFunctionalMethods.DomainNameBasicConfiguration;

import com.sun.org.apache.bcel.internal.generic.RETURN;

import java.util.HashMap;
import java.util.Map;

public class UrlBasicConfigtions {
    public static Map<String,String> GetUrlAppAuthentication(String Domain)
    {
        String[] strKeyDomain=Domain.split(";");
        Map<String,String> MapUrlTOAppAuthentication = new HashMap<String,String>();
        for(int num=1;num<strKeyDomain.length;num+=1)
        {
            if(Domain.contains("https://fjszyws.test1.59iedu.com")||Domain.contains(" https://zyws.59iedu.com"))
            {
                MapUrlTOAppAuthentication.put(Domain,"Basic eyJhcHBFbmQiOiJXRUIiLCJhcHBJZCI6IjE1MjIzYzU4MDdhOTQxN2M5ZDJmOWM1ZWQ3N2EyZmEyIiwicGxhdGZvcm1JZCI6IjA3NTQwZDc3MTEyMTQ2M2I5NDk4MmM4NGZqc3p5d3MxIiwicGxhdGZvcm1WZXJzaW9uSWQiOiIwNzU0MGQ3NzExMjE0NjNiOTQ5ODJjODRmanN6eXdzMCIsInByb2plY3RJZCI6IjQwMjg5Njc4NmU0NDlmMzkwMTZlNDQ5NWZqc3p5d3MwIiwic3ViUHJvamVjdElkIjoiNDAyODk2Nzg2ZTQ0OWYzOTAxNmU0Zmpzenl3czBzdWIifQ==");
            }
            else if (Domain.contains("https://gxzywspx.test1.59iedu.com")||Domain.contains("https://gxzyws.59iedu.com"))
            {
                MapUrlTOAppAuthentication.put(Domain,"Basic eyJhcHBFbmQiOiJXRUIiLCJhcHBJZCI6ImRjM2QyMzUwZDU2ZDRmOWU4YmQ3YjA0ZWIzNmQ5NWUzIiwicGxhdGZvcm1JZCI6IjQwMjg4MWM1N2E5ZjAzYzEwMTdhOWYwM2MxMjgwMDAwIiwicGxhdGZvcm1WZXJzaW9uSWQiOiI0MDI4ODFjNTdhOWYwM2MxMDE3YTlmMDNjMTI5MDAwMSIsInByb2plY3RJZCI6IjQwMjg5Njc4NmU0NDlmMzkwMTZlNDQ5Z3h6eXdzcHgwIiwic3ViUHJvamVjdElkIjoiNDAyODk2Nzg2ZTQ0OWYzOTAxNmVneHp5d3NweDBzdWIifQ==");
            }
            else if(Domain.contains("https://jskqv2.test1.59iedu.com")||Domain.contains("https://kq.59iedu.com"))
            {
                MapUrlTOAppAuthentication.put(Domain,"Basic eyJhcHBFbmQiOiJXRUIiLCJhcHBJZCI6Ijk4ODM0ZWU4NDcxYTQzMGQ5YTUwZTRlZDc2ZjI2YmEzIiwicGxhdGZvcm1JZCI6IjA3NTQwZDc3Y3AyMTQ2M2I5NDk4MmM4NDU2anNrcTAyIiwicGxhdGZvcm1WZXJzaW9uSWQiOiJjcDk1YmM0NzIxNDc0MTAwODA4YmNhNTg4Mmpza3F2MiIsInByb2plY3RJZCI6IjQwMjg5Njc4NmU0NDlmMzkwMTZlNDQ5ZjIwMjAwMnYyIiwic3ViUHJvamVjdElkIjoiNDAyODk2Nzg2ZTQ0OWYzOTAxNmU0NDlmMjAydjJzdWIifQ==");
            }
            else if(Domain.contains("https://btpxv2.test1.59iedu.com")||Domain.contains("https://www.zypxcs.com"))
            {
                MapUrlTOAppAuthentication.put(Domain,"Basic eyJhcHBFbmQiOiJXRUIiLCJhcHBJZCI6IjRjYTgyODFhODJjODQ0MjY4ODgzMzQ5NDU1YjlhNjFjIiwicGxhdGZvcm1JZCI6IjA3NTQwZDc3Y3AyMTQ2M2I5NDk4MmM4NDU2YnRweDc4IiwicGxhdGZvcm1WZXJzaW9uSWQiOiJmZjgwODA4MTc2ZGJmNjhmMDE3NmRiZjY4ZmJ0cHh2MiIsInByb2plY3RJZCI6IjQwMjg5Njc4NmU0NDlmMzkwMTZlMDUyNTIwYnRweHYyIiwic3ViUHJvamVjdElkIjoiNDAyODk2Nzg2ZTQ0OWYzOTAxNjA1MjVidHB4c3VidjIifQ==");
            }
        }
        return  MapUrlTOAppAuthentication;
    }
}
