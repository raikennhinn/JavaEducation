--�j�����ꂼ��̕��ϔN��킩��悤��select����

select sex,AVG(age) 
from employee 
group by sex;

--���SQL���Asex��0�Ȃ�'�j��'�A1�Ȃ�'����'�ɒu�������ĕ\������
--case�����g�p���邱��

select sex,AVG(age), 
case sex when 0 then '�j'
 when 1 then '��' end as ����
from employee
group by sex, ����;

--�������Ƃ̏]�ƈ��̐l�����J�E���g���ĕ\������
--�����Ɂi�Ȃ��j�Ɠ����Ă���ۂ�W�͖�������@�i��j�uECP�P�W�v�u�����J�����Q�ہv�̂悤�ɕ\��

select CONCAT(shozoku_bu,
case shozoku_ka when '�i�Ȃ��j' then ''
 else shozoku_ka end,
case shozoku_kakari when '�i�Ȃ��j' then ''
 else shozoku_kakari  end) as shozoku_name,
count(*) as shozoku_ninzu
from employee 
join shozoku
on employee.shozoku_code = shozoku.shozoku_code
group by employee.shozoku_code;


--���SQL���A10�l�ȉ��̏����͕\�����Ȃ��悤�ɏ����w�肷��
select CONCAT(shozoku_bu,
case shozoku_ka when '�i�Ȃ��j' then ''
 else shozoku_ka end,
case shozoku_kakari when '�i�Ȃ��j' then ''
 else shozoku_kakari  end) as shozoku_name,
count(*) as shozoku_ninzu
from employee 
join shozoku
on employee.shozoku_code = shozoku.shozoku_code
group by employee.shozoku_code
having shozoku_ninzu > 10;

--10��16���̔���グ��50000�ȏ�̎Ј��̂����AECP�����̏]�ƈ���No.�Ɩ��O�Ə����R�[�h�ƕ����iECP)��\��
--sell�e�[�u����join������@�ƁA
--sell�e�[�u���ւ̕��₢���킹��������@�ł��ꂼ��𓚂��邱��
--����グ����sell�e�[�u���ɑ��݂���
--���@�P
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

--���@�Q
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

--2017/10/17�̔���グ��90000�ȏ�̏]�ƈ��̍��v�l�����������Ƃɏo���B
--���֕��₢���킹�iexists�j���g�p���邱��

select 
CONCAT(sh.shozoku_bu,
case sh.shozoku_ka when '�i�Ȃ��j' then ''
 else sh.shozoku_ka end,
case sh.shozoku_kakari when '�i�Ȃ��j' then ''
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

