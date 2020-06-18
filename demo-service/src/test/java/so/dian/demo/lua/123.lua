--
-- Created by IntelliJ IDEA.
-- User: mc
-- Date: 2018/11/28
-- Time: 5:15 PM
-- To change this template use File | Settings | File Templates.
--

local idList = {};
for w in string.gmatch(KEYS[2], "([^',']+)") do
    table.insert(idList, w);
end;
local resultList = {};
for key, value in ipairs(idList) do
    KEYS[3]= KEYS[1] .. tostring(value);
 --   local key= cache_prefix.. tostring(value);
    table.insert(resultList, redis.call('GET', KEYS[3]));
end;
return table.concat(resultList, '&&');