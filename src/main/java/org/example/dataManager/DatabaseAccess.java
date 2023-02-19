package org.example.dataManager;

import org.example.portfolioComponents.asset.Asset;
import org.example.portfolioComponents.cash.Cash;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import java.util.List;

public class DatabaseAccess {

    SessionFactory sessionFactory;

    public DatabaseAccess() {
        StandardServiceRegistry ssr = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml").build();

        Metadata metadata = new MetadataSources(ssr).getMetadataBuilder().build();
        sessionFactory = metadata.getSessionFactoryBuilder().build();
    }
    public void updateDatabaseWalletV2(List<Asset> assetList,List<Cash> cashList){
            try(Session session = sessionFactory.openSession()){
                Transaction transaction = session.beginTransaction();
                updateCash(session,cashList);
                updateAsset(session,assetList);
                transaction.commit();
            }
    }
    private void updateCash(Session session,List<Cash> cashList){
        List<Cash> cashResults = session
                .createQuery("FROM Cash", Cash.class)
                .list();
        cashResults.forEach(session::delete);  //USUWAMY WSZYSTKO Z LISTY POBRANEJ Z BAZY DANYCH
        cashList.forEach(session::save);       //ZAPISUJEMY NASZĄ AKTUALNA CASHLISTE DO BAZY DANYCH
    }
    private void updateAsset(Session session,List<Asset> assetList){
        List<Asset> assetResults = session
                .createQuery("FROM Asset", Asset.class)
                .list();
        assetResults.forEach(session::delete);  //USUWAMY WSZYSTKO Z LISTY POBRANEJ Z BAZY DANYCH
        assetList.forEach(session::save);       //ZAPISUJEMY NASZĄ AKTUALNA ASSETLISTE DO BAZY DANYCH
    }
    protected List<Cash> getCash() {
        Session session = null;
        List results = null;
        try {   session = sessionFactory.openSession();
                String hql = "FROM Cash";
                Query query = session.createQuery(hql);
                results = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }   return results;
    }
    protected List<Asset> getAssets() {
        Session session = null;
        List results = null;
        try {   session = sessionFactory.openSession();
                String hql = "FROM Asset";
                Query query = session.createQuery(hql);
                results = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }return results;
    }
}
