--男女それぞれの平均年齢がわかるようにselectする

select sex,AVG(age) 
from employee 
group by sex;

--上のSQLを、sexが0なら'男性'、1なら'女性'に置き換えて表示する
--case文を使用すること

select sex,AVG(age), 
case sex when 0 then '男'
 when 1 then '女' end as 性別
from employee
group by sex, 性別;

--所属ごとの従業員の人数をカウントして表示する
--所属に（なし）と入っている課や係は無視する　（例）「ECP１係」「中央開発部２課」のように表示

select CONCAT(shozoku_bu,
case shozoku_ka when '（なし）' then ''
 else shozoku_ka end,
case shozoku_kakari when '（なし）' then ''
 else shozoku_kakari  end) as shozoku_name,
count(*) as shozoku_ninzu
from employee 
join shozoku
on employee.shozoku_code = shozoku.shozoku_code
group by employee.shozoku_code;


--上のSQLを、10人以下の所属は表示しないように条件指定する
select CONCAT(shozoku_bu,
case shozoku_ka when '（なし）' then ''
 else shozoku_ka end,
case shozoku_kakari when '（なし）' then ''
 else shozoku_kakari  end) as shozoku_name,
count(*) as shozoku_ninzu
from employee 
join shozoku
on employee.shozoku_code = shozoku.shozoku_code
group by employee.shozoku_code
having shozoku_ninzu > 10;

--10月16日の売り上げが50000以上の社員のうち、ECP所属の従業員のNo.と名前と所属コードと部名（ECP)を表示
--sellテーブルをjoinする方法と、
--sellテーブルへの副問い合わせをする方法でそれぞれ解答すること
--売り上げ情報はsellテーブルに存在する
--方法１
select 
	e.employee_no,
	e.employee_name,
	e.shozoku_code,
	sh.shozoku_bu
from employee e
join shozoku sh
on e.shozoku_code = sh.shozoku_code
join sell s
on e.employee_no = s.employee_no
where s.sell >= 50000 
and sh.shozoku_bu='ECP' 
and s.sell_date = '2017/10/16';

--方法２
select 
	e.employee_no,
	e.employee_name,
	e.shozoku_code,
	sh.shozoku_bu
from employee e 
join shozoku sh
on e.shozoku_code = sh.shozoku_code
where e.employee_no in (
	select sell.employee_no
	from sell
	where 
		sell >= 50000
		and sell_date ='2017/10/16'
	)
	and sh.shozoku_bu='ECP';

--2017/10/17の売り上げが90000以上の従業員の合計人数を所属ごとに出す。
--相関副問い合わせ（exists）を使用すること

select 
CONCAT(sh.shozoku_bu,
case sh.shozoku_ka when '（なし）' then ''
 else sh.shozoku_ka end,
case sh.shozoku_kakari when '（なし）' then ''
 else sh.shozoku_kakari  end) as shozoku_name,
COUNT(e.employee_no)
from employee e
join shozoku sh
on e.shozoku_code = sh.shozoku_code
where exists(
	select 1
	from sell
	where
	sell.employee_no = e.employee_no
	and sell.sell >= 80000
	and sell_date ='2017/10/17'
	)
group by shozoku_name;

