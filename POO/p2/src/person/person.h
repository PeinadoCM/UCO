#ifndef PERSON_H
    #define PERSON_H
    #include <string>
    #include <vector>
    #include <iostream>
    class Person{
        private:
            std::string name_;
            int age_;
            double rank_;
            std::vector<std::string> preferences_;
        public:
            Person(std::string name, int age, double rank) {name_=name;age_=age;rank_=rank;}
            std::string GetName(){return name_;}
            int GetAge(){return age_;}
            double GetRank(){return rank_;}
            std::string GetDataStr(){
                return name_ + " " + std::to_string(age_) + " " + std::to_string(rank_) + "\n";
            }
            std::vector<std::string> GetPreferences(){return preferences_;}
            void AddPreference(std::string s1){preferences_.push_back(s1);}
            void AddPreferences(std::string s1, std::string s2, std::string s3);
            void ShowPreferences();
            void ChangePreference(int pos, std::string s1);
    };

#endif