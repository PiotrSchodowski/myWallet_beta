package org.example.dataManager.databaseHandling;

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

    public List getAssets() {
         Session session = null;
         List results = null;
        try {
            session = sessionFactory.openSession();

            String hql = "FROM Asset";
            Query query = session.createQuery(hql);
            results = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return results;
    }
    public List getCash() {
        Session session = null;
        List results = null;
        try {
            session = sessionFactory.openSession();

            String hql = "FROM Cash";
            Query query = session.createQuery(hql);
            results = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return results;
    }
    public void updateDatabaseWallet(List assetList, List cashList) {
        Session session = null;
        Transaction transaction = null;
        List results = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            Asset asset;
            String hql = "FROM Asset";
            Query query = session.createQuery(hql);
            results = query.list();                    //POBIERAMY LISTĘ Z BAZY DANYCH ZEBY WIEDZIEC JAKA JEST DLUGA

            for(int i = 0; i < results.size(); i++){  //USUWAMY WSZYSTKO
                asset = (Asset) results.get(i);
                session.delete(asset);
            }
            for(int i = 0; i < assetList.size(); i++){  //ZAPISUJEMY AKTUALNĄ LISTĘ (PODMIENIAMY)
                asset = (Asset) assetList.get(i);
                session.save(asset);
            }
            Cash cash;
            String hqlCash = "FROM Cash";
            Query queryCash = session.createQuery(hqlCash);
            results = queryCash.list();                    //POBIERAMY LISTĘ Z BAZY DANYCH ZEBY WIEDZIEC JAKA JEST DLUGA

            for(int i = 0; i < results.size(); i++){  //USUWAMY WSZYSTKO
                cash = (Cash) results.get(i);
                session.delete(cash);
            }
            for(int i = 0; i < cashList.size(); i++){  //ZAPISUJEMY AKTUALNĄ LISTĘ (PODMIENIAMY)
                cash = (Cash) cashList.get(i);
                session.save(cash);
            }
            transaction.commit();

        } catch(Exception e) {
            e.printStackTrace();
            if(transaction != null) transaction.rollback();
        } finally {
            if(session != null) session.close();
        }
    }
}
