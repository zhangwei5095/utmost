from org.utmost.common import SpringContext
from org.utmost.portal.service import AutoService
from java.util import ArrayList
from java.util import HashMap

autoService=SpringContext.getBean("AutoService")

U_BANK_WASTEBOOK="U_BANK_WASTEBOOK"

def test():
    print 'test...'

#业务预警
def riskBusinessType(BUSSTYPE):
    hql='from '+U_BANK_WASTEBOOK+' v where v.busstype=\''+BUSSTYPE+'\'';
#    hql='from '+U_BANK_WASTEBOOK+''
    print 'hql:'+hql
    arraylist=autoService.findByHql(hql)
    print arraylist.size()
    for m in arraylist:
        print m
        print 'uuid:'+m.get("uuid")
        print 'uniseqno:'+m.get("uniseqno")
        print 'brno:'+m.get("brno")
        
        map=HashMap()
        map.put('uuid','1111111111');
        saveRiskInfo(map)
    return 'abc'

#保存预警信息到数据表
def saveRiskInfo(map):
#    autoService.save('规则表名',map);
    autoService.exteriorSave('TEST1',map);
    print 'savaRiskInfo'+map.toString()

#riskBusinessType('111')

#风险保存
def saveRiskInfo(entityName,list,rulecode):
    riskList=autoService.findByHql("from U_BANK_RISKRULEINFO v where v.rulecode='"+rulecode+"'");
    if riskList !='null' and riskList.size()>0 :
    	riskinfoList = ArrayList()
        riskHM = riskList.get(0)
        for entity in list:
           riskinfoMap= HashMap()
           riskinfoMap.put("uniseqno",entity.get("uniseqno"))
           riskinfoMap.put("trxdate",entity.get("trxdate"))
           riskinfoMap.put("trxtime",entity.get("trxtime"))
           riskinfoMap.put("tellerno",entity.get("tellerno"))
           riskinfoMap.put("custname",entity.get("rcustname"))
           riskinfoMap.put("brno",entity.get("brno"))
           riskinfoMap.put("riskdate","20090210")
           riskinfoMap.put("risktime","110911")
           riskinfoMap.put("risklevel",riskHM.get("risklevel"))
           riskinfoMap.put("riskcode",riskHM.get("rulecode"))
           riskinfoMap.put("riskname",riskHM.get("rulename"))
           riskinfoMap.put("risktype",riskHM.get("risktype"))
           riskinfoList.add(riskinfoMap)     
        autoService.saveAll(entityName, riskinfoList)
        
test() 
#saveRiskInfo(U_BANK_RISKINFO,)                         