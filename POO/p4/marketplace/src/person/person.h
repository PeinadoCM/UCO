#ifndef PERSON_H
    #define PERSON_H
    #include <string>
    #include <vector>
    #include <iostream>
    class Person{
        private:
            std::string id_;
            std::string name_;
            std::string town_;
            std::string province_;
            std::string country_;            
            int age_;
            double rank_;
            int entry_year_;
            std::vector<std::string> preferences_;

        public:
            Person(std::string id, std::string name="empty", std::string town="empty", std::string province="empty", std::string country="empty", int age=0, double rank=0, int entry_year=0);

            std::string GetId(){return id_;}
            std::string GetName(){return name_;}
            std::string GetTown(){return town_;}
            std::string GetProvince(){return province_;}
            std::string GetCountry(){return country_;}
            int GetAge(){return age_;}
            double GetRank(){return rank_;}
            int GetEntryYear(){return entry_year_;}
            std::string GetDataStr();
            std::vector<std::string> GetPreferences(){return preferences_;}
            
            void SetId(std::string id){id_=id;}
            void SetName(std::string name){name_=name;}
            void SetTown(std::string town){town_=town;}
            void SetProvince(std::string province){province_=province;}
            void SetCountry(std::string country){country_=country;}
            void SetRank(double rank){rank_=rank;}
            bool SetAge(int age);
            bool SetEntryYear(int entry_year);

            void AddPreference(std::string s1){preferences_.push_back(s1);}
            void AddPreferences(std::string s1, std::string s2, std::string s3);
            void ShowPreferences();
            void ChangePreference(int pos, std::string s1);
    };

#endif
