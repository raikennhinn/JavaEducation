--男女それぞれの平均年齢がわかるようにselectする
select sex, avg(age) 
from employee 
group by sex;

--性別をコードから'男性''女性'に置き換える
select 
case sex
  when 0 then '男性'
  when 1 then '女性'
  else '不明'
end as sex,
avg(age) 
from employee 
group by sex;

--所属ごとの従業員の人数をカウントして表示する。
select 
  concat(
    s.shozoku_bu,
    case s.shozoku_ka
      when '（なし）' then ''
      else s.shozoku_ka
    end,
    case s.shozoku_kakari
      when '（なし）' then ''
      else s.shozoku_kakari
    end
  ) shozoku,
  count(e.employee_no)
from employee e
join shozoku s
on e.shozoku_code = s.shozoku_code
group by shozoku;

--10人以下の所属は表示しないようにする
select 
  concat(
    s.shozoku_bu,
    case s.shozoku_ka
      when '（なし）' then ''
      else s.shozoku_ka
    end,
    case s.shozoku_kakari
      when '（なし）' then ''
      else s.shozoku_kakari
    end
  ) shozoku,
  count(e.employee_no)
from employee e
join shozoku s
on e.shozoku_code = s.shozoku_code
group by shozoku
having count(e.employee_no) >= 11;

--10月16日の売り上げが50000以上の社員のうち、ECP所属の従業員のNo.と名前と所属コードと部名（ECP)を表示
--sellテーブルをjoinする方法と、sellテーブルへの副問い合わせをする方法でそれぞれ解答すること
--売り上げ情報はsellテーブルに存在する
--【解１】
select e.employee_no, e.employee_name, szk.shozoku_code, szk.shozoku_bu 
from employee e
join sell s
on e.employee_no = s.employee_no
join shozoku szk
on e.shozoku_code = szk.shozoku_code
where s.sell_date = '2017/10/16'
and s.sell >= 50000
and szk.shozoku_bu = 'ECP';

--【解２】
select e.employee_no, e.employee_name, szk.shozoku_code, szk.shozoku_bu 
from employee e
join shozoku szk
on e.shozoku_code = szk.shozoku_code
where e.employee_no in
(
 select employee_no
 from sell
 where sell_date = '2017/10/16'
 and sell >= 50000
)
and szk.shozoku_bu = 'ECP'
;


--2017年10月17日の売り上げが90000以上の従業員の合計人数を部ごとで出す。
--相関副問い合わせを使用すること

select 
  concat(
    sh.shozoku_bu,
    case sh.shozoku_ka
      when '（なし）' then ''
      else sh.shozoku_ka
    end,
    case sh.shozoku_kakari
      when '（なし）' then ''
      else sh.shozoku_kakari
    end
  ) as shozoku,
count(e.employee_no) as total 
from employee e
join shozoku sh
on e.shozoku_code = sh.shozoku_code
where exists (
	select 1
	from sell s
	where s.employee_no = e.employee_no
	and s.sell >= 90000
	and s.sell_date = '2017/10/17'
)
group by shozoku;


select 
  concat(
    sh.shozoku_bu,
    case sh.shozoku_ka
      when '（なし）' then ''
      else sh.shozoku_ka
    end,
    case sh.shozoku_kakari
      when '（なし）' then ''
      else sh.shozoku_kakari
    end
  ) as shozoku,
count(e.employee_no) as total 
from employee e
join shozoku sh
on e.shozoku_code = sh.shozoku_code
group by shozoku;

