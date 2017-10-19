--�j�����ꂼ��̕��ϔN��킩��悤��select����
select sex, avg(age) 
from employee 
group by sex;

--���ʂ��R�[�h����'�j��''����'�ɒu��������
select 
case sex
  when 0 then '�j��'
  when 1 then '����'
  else '�s��'
end as sex,
avg(age) 
from employee 
group by sex;

--�������Ƃ̏]�ƈ��̐l�����J�E���g���ĕ\������B
select 
  concat(
    s.shozoku_bu,
    case s.shozoku_ka
      when '�i�Ȃ��j' then ''
      else s.shozoku_ka
    end,
    case s.shozoku_kakari
      when '�i�Ȃ��j' then ''
      else s.shozoku_kakari
    end
  ) shozoku,
  count(e.employee_no)
from employee e
join shozoku s
on e.shozoku_code = s.shozoku_code
group by shozoku;

--10�l�ȉ��̏����͕\�����Ȃ��悤�ɂ���
select 
  concat(
    s.shozoku_bu,
    case s.shozoku_ka
      when '�i�Ȃ��j' then ''
      else s.shozoku_ka
    end,
    case s.shozoku_kakari
      when '�i�Ȃ��j' then ''
      else s.shozoku_kakari
    end
  ) shozoku,
  count(e.employee_no)
from employee e
join shozoku s
on e.shozoku_code = s.shozoku_code
group by shozoku
having count(e.employee_no) >= 11;

--10��16���̔���グ��50000�ȏ�̎Ј��̂����AECP�����̏]�ƈ���No.�Ɩ��O�Ə����R�[�h�ƕ����iECP)��\��
--sell�e�[�u����join������@�ƁAsell�e�[�u���ւ̕��₢���킹��������@�ł��ꂼ��𓚂��邱��
--����グ����sell�e�[�u���ɑ��݂���
--�y���P�z
select e.employee_no, e.employee_name, szk.shozoku_code, szk.shozoku_bu 
from employee e
join sell s
on e.employee_no = s.employee_no
join shozoku szk
on e.shozoku_code = szk.shozoku_code
where s.sell_date = '2017/10/16'
and s.sell >= 50000
and szk.shozoku_bu = 'ECP';

--�y���Q�z
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


--2017�N10��17���̔���グ��90000�ȏ�̏]�ƈ��̍��v�l���𕔂��Ƃŏo���B
--���֕��₢���킹���g�p���邱��

select 
  concat(
    sh.shozoku_bu,
    case sh.shozoku_ka
      when '�i�Ȃ��j' then ''
      else sh.shozoku_ka
    end,
    case sh.shozoku_kakari
      when '�i�Ȃ��j' then ''
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
      when '�i�Ȃ��j' then ''
      else sh.shozoku_ka
    end,
    case sh.shozoku_kakari
      when '�i�Ȃ��j' then ''
      else sh.shozoku_kakari
    end
  ) as shozoku,
count(e.employee_no) as total 
from employee e
join shozoku sh
on e.shozoku_code = sh.shozoku_code
group by shozoku;

