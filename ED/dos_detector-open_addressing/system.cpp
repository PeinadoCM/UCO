/**
 * @file system.cpp
 *
 * CopyRight F. J. Madrid-Cuevas <fjmadrid@uco.es>
 *
 * Sólo se permite el uso de este código en la docencia de las asignaturas sobre
 * Estructuras de Datos de la Universidad de Córdoba.
 *
 * Está prohibido su uso para cualquier otro objetivo.
 */

#include <string>
#include <sstream>

#include "system.hpp"
#include "hash_table_imp.hpp"

void OS::remove_banned_ips(size_t now)
{
    std::vector<IP> ips;

    // TODO: Scan the table of banned ips to push back in vector ips the banned ips
    // whose ban time was finished at this moment (time <= now).
    for(size_t i=0; banned_ips_.size()>i; i++){
        if((banned_ips_.entry(i)).is_valid()){
            if((banned_ips_.entry(i)).value() <= now){
                ips.push_back((banned_ips_.entry(i)).key());
            }
        }
    }
    //

    std::cout << "IPs which ban time was finished at time " << now << " [";
    for (size_t i = 0; i < ips.size(); ++i)
        std::cout << ' ' << ips[i];
    std::cout << " ]" << std::endl;

    // TODO: Remove the ips from the banned_ips_ table.
    for(size_t i=0; ips.size()>i; i++){
        banned_ips_.entry(banned_ips_.find(ips[i])).set_deleted();
    }
    //

#ifndef NDEBUG
    for (auto const &ip : ips)
        assert(!banned_ips_.has(ip));
#endif
}

void OS::ban_ip(IP const &ip, size_t ban_time)
{
    std::cout << "Ban ip: " << ip << " from time " << time_ << " to " << time_ + ban_time << std::endl;
    // TODO
    // Remember: Insert in banned_ips_ table the time when the ban
    // time will be finished.
    banned_ips_.insert(ip, ban_time + time_);
    //
}

bool OS::is_banned(IP const &ip) const
{
    bool ret_val = false;
    // TODO
    // Hint: Has banned_ips_ table that ip?
    int pos=banned_ips_.find(ip);
    if(banned_ips_.entry(pos).is_valid()){
        ret_val=true;
    }
    //
    return ret_val;
}

std::vector<std::pair<IP, size_t>>
OS::banned_ips() const
{
    std::vector<std::pair<IP, size_t>> ips;
    // TODO
    // Hint: use const_cast to remove const from this.
    IP auxip;
    size_t aux;
    for(size_t i=0; i<banned_ips_.size(); i++){
        if((banned_ips_.entry(i)).is_valid()){
            auxip=(banned_ips_.entry(i)).key();
            aux=(banned_ips_.entry(i)).value();
            ips.push_back(std::make_pair(auxip,aux));
        }
    }
    //
    return ips;
}

////////////////////////////////////////////////
// Other system operations.
///////////////////////////////////////////////

std::istream &
operator>>(std::istream &in, Log &log) noexcept(false)
{
    log.resize(0);
    std::string buffer;
    std::istringstream input;
    LogEntry entry;
    while (std::getline(in, buffer))
    {
        if (buffer == "")
            continue;
        input.str(buffer);
        input.clear();
        input >> entry.time >> entry.ip;
        if (!input)
            throw std::runtime_error("Wrong input format for log entry.");
        log.push_back(entry);
    }
    return in;
}

OS::OS() : time_(0), kill_(false), banned_ips_(16, ip_to_int,
                                               LPHash::create(UHash(16)))
{
}

size_t
OS::time() const
{
    return time_;
}

size_t
OS::sleep(size_t seconds)
{
    time_ += seconds;
    remove_banned_ips(time_);
    if (kill_)
        return seconds;
    else
        return 0;
}

void OS::kill()
{
    kill_ = true;
}

OS &System()
{
    static std::shared_ptr<OS> system_;
    if (!system_)
        system_ = std::make_shared<OS>();
    return *system_;
}

std::ostream &
operator<<(std::ostream &out, std::vector<std::pair<IP, size_t>> const &ips)
{
    out << "[";
    for (size_t i = 0; i < ips.size(); ++i)
        out << ' ' << ips[i].first << ':' << ips[i].second;
    out << " ]";
    return out;
}
